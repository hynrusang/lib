/*
js로 html 요소를 동적으로 더 쉽게 다룰 수 있게 해 줍니다.
작성자: 환류상
 */
/**
 * @deprecated This class is not supported starting with 1.2.
 */
class SecurityError extends Error {
    constructor(massage) {
        console.warn("This class is not supported starting with 1.2.");
        super(massage);
        this.name = "Security Error";
    }
}
const FragDom = class {
    #node;
    /**
     * @deprecated this property was change to private. use node getter instead.
     * @type {HTMLElement}
     */
    get _node() {
        console.warn("this property was change to private.\nuse node getter instead.");
        return this.#node;
    };
    /**
     * @type {() => HTMLElement}
     */
    get node() {
        return this.#node;
    }
    /**
     * @type {(additional: object?) => FragDom}
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
     * @type {(num: number) => FragDom}
     */
    remove = num => {
        this.#node.removeChild(this.children(num).node);
        return this;
    }
    /**
     * @type {(num: number) => FragDom}
     */
    children = num => this.#node.children[num] ? new FragDom(this.#node.children[num]) : null;
    /**
     * @type {(...dom: FragDom | FragDom[]) => FragDom}
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
     * @type {(...dom?: FragDom | FragDom[]) => FragDom}
     */
    reset = (...dom) => {
        this.#node.innerHTML = "";
        this.add(...dom);
        return this;
    }
    /**
     * @type {(count: number) => FragDom[]}
     * @deprecated This method is not supported starting with 1.2.
     */
    copy = count => {
        console.warn("This method is not supported starting with 1.2.");
        const tempbox = [];
        for (let i = 0; i < count; i++) tempbox.push($(this.#node.cloneNode(true)));
        return tempbox;
    }
    /**
     * @type {(node: string | HTMLElement, additional: Object?) => FragDom}
     */
    constructor(node, additional) {
        this.#node = (typeof node === "string") ? document.createElement(node) : node;
        if (typeof additional !== 'undefined') this.set(additional);
    }
}
/**
 * @type {(node: string | HTMLElement, additional?: Object) => FragDom}
 */
const $ = (node, additional) => new FragDom(node, additional);
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
 * (selector: `!${string}`) => FragDom[]
 * (selector: string) => FragDom
 * (selector: HTMLElement) => FragDom
* }}
*/
const snipe = selector => {
   const temp = ((typeof selector == "string") && (selector[0] == "!")) ? [] : $(scan(selector));
   if (typeof temp == "object") for (let i = 0; i < scan(selector).length; i++) temp.push($(scan(selector)[i]));
   return temp;
}
const Fragment = class {
    #view;
    #domlist;
    #action;
    /**
     * @type {() => Fragment}
     */
    launch = () => {
        this.#view.reset(this.#domlist)
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
     * @type {(view: String, ...domlist: FragDom) => Fragment}
     */
    constructor(view, ...domlist) {
        this.#view = snipe(`fragment[rid=${view}]`);
        this.#domlist = domlist;
    }
}
/**
 * @type {(jhpath: string) => void}
 * @throws {SecurityError}
 * @deprecated This function is not supported starting with 1.2. Use Fragment.launch() instead.
 */
const loading  = jhpath => {
    console.warn("This function is not supported starting with 1.2.\nUse Fragment.launch() instead.");
    const REQUEST = new XMLHttpRequest();
    if (jhpath.indexOf("http") != -1) throw new SecurityError("loading 함수로 다른 웹사이트의 htm.js를 로딩할 수 없습니다.");
    else {
        REQUEST.open('GET', `${jhpath}.htm.js`);
        REQUEST.send();
        REQUEST.onreadystatechange = (e => { (e.target.readyState == 4) ? eval(REQUEST.response) : null});
    }
}
