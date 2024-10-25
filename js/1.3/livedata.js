/*
js에서 값의 변화를 관측하는 LiveData를 비롯한 여러 기능들을 사용할 수 있게 해줍니다.
작성자: 환류상
 */
const LiveData = class {
    #data;
    #type;
    #observer;
    set value(data) {
        if (this.#type && this.#type.name.toLocaleLowerCase() !== (Array.isArray(data) ? "array" : typeof data)) throw new TypeError(`invalid type of data. Data must be of type ${this.#type.name}.`);
        const isChanged = JSON.stringify(data) !== JSON.stringify(this.#data);
        this.#data = data;
        if (isChanged && typeof this.#observer == "function") this.#observer();
    }
    get value() {
        return (typeof this.#data == "object") ? JSON.parse(JSON.stringify(this.#data)) : this.#data;
    }
    /**
     * @type {(data: Any, dataset: object) => LiveData}
     */
    constructor(data, dataset) {
        this.#data = data;
        if (dataset) {
            this.#type = dataset.type;
            this.#observer = dataset.observer;
        }
    }
}
const LiveManager = class {
    #editable;
    #livedataObject;
    value = (id, data) => {
        if (typeof data !== "undefined") {
            const isChanged = JSON.stringify(this.#livedataObject[id].value) !== JSON.stringify(data);
            this.#livedataObject[id].value = data;
            return isChanged;
        } else return this.#livedataObject[id].value;
    }
    get id() {
        if (!this.#editable) throw new SyntaxError(`This LiveDataManager cannot be accessed or modified externally.`)
        return this.#livedataObject;
    }
    toArray = () => Object.values(this.#livedataObject).map(inner => inner.value);
    toObject = () => Object.entries(this.#livedataObject).reduce((obj, [key, value]) => {
        obj[key] = value.value;
        return obj;
      }, {});
    /**
     * @type {(livedataObject: Object, editable?: boolean) => LiveDataManager}
     */
    constructor(livedataObject, editable = true) {
        if ("object" !== (Array.isArray(livedataObject) ? "array" : typeof livedataObject)) throw new TypeError(`invalid type of livedataObject. livedataObject must be of type Object. (livedataObject: ${Array.isArray(livedataObject) ? "array" : typeof livedataObject})`);
        this.#editable = editable;
        this.#livedataObject = Object.entries(livedataObject).reduce((obj, [key, value]) => { 
            if (!(value instanceof LiveData)) throw new TypeError(`invalid type of ${key}'s value. ${key}'s value must be of instance LiveData`)
            else obj[key] = value;
            return obj;
        }, {});
    }
}
const LiveBinder = class {
    static #bindlist = {};
    static #synclist = {};
    static #sync = obj => {
        const varValue = obj.attributes.var.value.split("=")[0];
        if (typeof this.#synclist[varValue] === "object") for (let element of this.#synclist[varValue]) this.#innerSync(element, element.attributes.exp.value);
    }
    static #innerSync = (obj, expression) => {
        const subStrings = obj.attributes.exp.value.split("->")[0].replaceAll(" ", "").split(",").filter(value => value != "");
        let returnString = expression;
        for (let match of returnString.match(/\{(.+?)\}/g)) {
            let replaced = match;
            for (let subString of subStrings) replaced = replaced.replaceAll(subString, `__#${subString}__`).replaceAll(/eval\s*\([^)]*\)/g, `__#${subString}__`).replaceAll(/new\s*Function\s*\([^)]*\)/g, `__#${subString}__`);
            returnString = returnString.replace(match, `${replaced}`);
        }
        for (let subString of subStrings) {
            const parsing = ["INPUT", "TEXTAREA"].includes(this.#bindlist[subString].nodeName) ? this.#bindlist[subString].value : this.#bindlist[subString].innerText;
            if (parsing[0] == "$") returnString = returnString.replaceAll(`__#${subString}__`, Math[parsing.split("$")[1]]);
            else if (isNaN(parsing) || parsing == "" || /^0[0-9]/g.test(parsing.trim().replace("-", "").replace("+", ""))) returnString = returnString.replaceAll(`__#${subString}__`, `"${parsing.replace(/"/g, '\\"').replaceAll("{", "").replaceAll("}", "").replaceAll("<->", "↔").replaceAll("->", "→").replaceAll("<-", "←")}"`)
            else returnString = returnString.replaceAll(`__#${subString}__`, parsing);
        }
        returnString = returnString.replaceAll(/\{([^{}]+)\}/g, (match, group) => {
            const result = eval(group);
            return result;
        });
        if (["INPUT", "TEXTAREA"].includes(obj.nodeName)) obj.value = returnString.split("->")[1];
        else obj.innerText = returnString.split("->")[1];
    }
    static _set = () => {
        this.#synclist = {};
        for (let element of document.querySelectorAll("[var]")) {
            const varValues = element.attributes.var.value.split("=");
            this.#bindlist[varValues[0]] = element;
            if (varValues[1] && varValues[1] != "") {
                if (["INPUT", "TEXTAREA"].includes(element.nodeName)) element.value = varValues[1];
                else element.innerText = varValues[1];
            }
            element.addEventListener('input', () => this.#sync(element));
        }
        for (let element of document.querySelectorAll("[exp]")) {
            for (let name of element.attributes.exp.value.split("->")[0].replaceAll(" ", "").split(",").filter(value => value != "")) {
                this.#synclist[name] ? this.#synclist[name].push(element) : this.#synclist[name] = [element];
                this.#innerSync(element, element.attributes.exp.value)
            }
        }
    }
    /**
     * @type {(id: String) => HTMLElement}
     */
    static find = id => this.#bindlist[id];
    /**
     * @type {(id: String, value: String) => void}
     */
    static define = (id, value) => {
        const element = document.createElement("vdom");
        element.setAttribute("var", id);
        element.innerText = value;
        this.#bindlist[id] = element;
    }
    /**
     * @type {(id: String, value: String) => void}
     */
    static update = (id, value) => {
        const element = this.find(id);
        if (["INPUT", "TEXTAREA"].includes(element.nodeName)) element.value = value;
        else element.innerText = value;
        this.#sync(element);
    }
}
const observer = new MutationObserver(mutationsList => {
    const mutation = mutationsList[mutationsList.length - 1];
    for (const node of [...mutation.addedNodes, ...mutation.removedNodes]) if (node instanceof HTMLElement) LiveBinder._set();
}).observe(document.body, { childList: true, subtree: true });
LiveBinder._set();
