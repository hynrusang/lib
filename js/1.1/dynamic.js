/*
js로 html 요소를 동적으로 더 쉽게 다룰 수 있게 해 줍니다.
작성자: 환류상
 */
/**
 * @deprecated This class is not supported starting with 1.2.0. Use Fragment.launch() instead.
 */
class SecurityError extends Error {
    constructor(massage) {
        console.log("%cThis class is not supported starting with 1.2.0.\nUse Fragment.launch() instead.", "color: red");
        super(massage);
        this.name = "Security Error";
    }
}
const Dom = class {
    #node;
    /**
     * @deprecated this property was change to private. use node getter instead.
     * @type {HTMLElement}
     */
    get _node() {
        console.log("%cthis property was change to private.\nuse node getter instead.", "color: red");
        return this.#node;
    };
    /**
     * @type {() => HTMLElement}
     */
    get node() {
        return this.#node;
    }
    /**
     * @type {(additional: object?) => Dom}
     */
    set = additional => {
        if (typeof additional === 'object') {
            for (const [key, value] of Object.entries(additional)) {
                if (["innerHTML", "html"].includes(key)) this.#node.innerHTML = value
                else if (["innerText", "text"].includes(key)) this.#node.innerText = value
                else if (key.indexOf("on") != -1 || key == "async") this.#node[key] = value
                else this.#node.setAttribute(key, value);
            }
        } else throw new TypeError('Additional parameter must be an {key: value} object');
        return this;
    };
    /**
     * @type {(num: number) => Dom}
     */
    remove = num => {
        this.#node.removeChild(this.children(num).node);
        return this;
    }
    /**
     * @type {(num: number) => Dom}
     */
    children = num => this.#node.children[num] ? new Dom(this.#node.children[num]) : null;
    /**
     * @type {(...dom: Dom | Dom[]) => Dom}
     */
    add = (...dom) => {
        for (let pdom of dom) {
            if (Array.isArray(pdom)) {
                for (let cdom of pdom) this.#node.appendChild(cdom.#node);
            } else this.#node.appendChild(pdom.#node);
        }
        return this;
    }
    /**
     * @type {(...dom?: Dom | Dom[]) => Dom}
     */
    reset = (...dom) => {
        this.#node.innerHTML = "";
        this.add(...dom);
        return this;
    }
    /**
     * @type {(count: number) => Dom[]}
     * @deprecated This method is not supported starting with 1.2.0.
     */
    copy = count => {
        console.log("%cThis method is not supported starting with 1.2.0.", "color: red");
        const tempbox = [];
        for (let i = 0; i < count; i++) tempbox.push($(this.#node.cloneNode(true)));
        return tempbox;
    }
    /**
     * @type {(node: string | HTMLElement, additional: Object?) => Dom}
     */
    constructor(node, additional) {
        this.#node = (typeof node === "string") ? document.createElement(node) : node;
        if (typeof additional !== 'undefined') this.set(additional);
    }
}
const Fragment = class {
    #view;
    #fragment;
    #action;
    /**
     * @type {() => Fragment}
     */
    launch = () => {
        this.#view.reset(this.#fragment)
        if (typeof this.#action == "function") this.#action();
        return this;
    }
    /**
     * @type {(action: Function) => Fragment}
     */
    registAction = action => {
        this.#action = action;
        return this;
    }
    /**
     * @type {(view: String, ...fragment: Dom) => Fragment}
     */
    constructor(view, ...fragment) {
        this.#view = snipe(`fragment[rid=${view}]`);
        this.#fragment = fragment;
    }
}
/**
 * @type {(node: string | HTMLElement, additional?: Object) => Dom}
 */
const $ = (node, additional) => new Dom(node, additional);
/** 
 * @type {{
* (selector: `!${string}`) => NodeListOf<HTMLElement>;
* (selector: string) => HTMLElement
* (selector: HTMLElement) => HTMLElement
* }}
*/
const scan = selector => (typeof selector == "string") ? (selector[0] == "!") ? document.querySelectorAll(selector.split("!")[1]) : document.querySelector(selector) : selector;
/**
 * @type {{
* (selector: `!${string}`) => Dom[]
* (selector: string) => Dom
* (selector: HTMLElement) => Dom
* }}
*/
const snipe = selector => {
   const temp = ((typeof selector == "string") && (selector[0] == "!")) ? [] : $(scan(selector));
   if (typeof temp == "object") for (let i = 0; i < scan(selector).length; i++) temp.push($(scan(selector)[i]));
   return temp;
}
/**
 * @type {(jhpath: string) => void}
 * @throws {SecurityError}
 * @deprecated This function is not supported starting with 1.2.0. Use Fragment.launch() instead.
 */
const loading  = jhpath => {
    console.log("%cThis function is not supported starting with 1.2.0.\nUse Fragment.launch() instead.", "color: red");
    const REQUEST = new XMLHttpRequest();
    if (jhpath.indexOf("http") != -1) throw new SecurityError("loading 함수로 다른 웹사이트의 htm.js를 로딩할 수 없습니다.");
    else {
        REQUEST.open('GET', `${jhpath}.htm.js`);
        REQUEST.send();
        REQUEST.onreadystatechange = (e => { (e.target.readyState == 4) ? eval(REQUEST.response) : null});
    }
}
