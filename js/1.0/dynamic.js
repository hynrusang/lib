/*
js로 html 요소를 동적으로 더 쉽게 다룰 수 있게 해 줍니다.
작성자: 환류상
 */
class SecurityError extends Error {
    constructor(massage) {
        super(massage);
        this.name = "Security Error";
    }
}
class DomDefault {
    /**
     * @type {HTMLElement}
     */
    _node;
    /**
     * @type {(num: number) => Dom}
     */
    children = num => this._node.children[num] ? new Dom(this._node.children[num]) : null;
    /**
     * @type {(...dom: DomDefault | DomDefault[]) => DomDefault}
     */
    add = (...dom) => {
        for (let pdom of dom) {
            if (Array.isArray(pdom)) {
                for (let cdom of pdom) this._node.appendChild(cdom._node);
            } else this._node.appendChild(pdom._node);
        }
        return this;
    }
    /**
     * @type {(num: number) => DomDefault}
     */
    remove = num => { 
        this._node.removeChild(this.children(num).node); 
        return this;
    }
    /**
     * @type {(node: string | HTMLElement) => DomDefault}
     */
    constructor(node = "div") { this._node = is(node, "string") ? document.createElement(node) : node; }
}

class DomExpert extends DomDefault {
    /**
     * @type {(count: number) => DomExpert[]}
     */
    copy = count => {
        const tempbox = [];
        for (let i = 0; i < count; i++) tempbox.push($(this._node.cloneNode(true)));
        return tempbox;
    }
    /**
     * @type {(...dom?: DomExpert | DomExpert[]) => DomExpert}
     */
    reset = (...dom) => {
        this._node.innerHTML = "";
        this.add(...dom);
        return this;
    }
    /**
     * @type {(additional: Object?) => DomExpert}
     */
    set = additional => {
        if (typeof additional === 'object') {
            for (const [key, value] of Object.entries(additional)) {
                if (["innerHTML", "html"].includes(key)) this._node.innerHTML = value
                else if (["innerText", "text"].includes(key)) this._node.innerText = value
                else if (key.indexOf("on") != -1 || key == "async") this._node[key] = value
                else this._node.setAttribute(key, value);
            }
        } else throw new Error('Additional parameter must be an {key: value} object');
        return this;
    };
    /**
     * @type {(node: string | HTMLElement, additional: Object?) => DomExpert}
     */
    constructor(node, additional) {
        super(node);
        this.set(additional);
    }
}
/**
 * @type {(node: string | HTMLElement, additional?: Object) => DomExpert}
 */
const $ = (node, additional) => { return new DomExpert(node, additional); };
/**
 * @deprecated This function is not supported starting with 1.1.
 * @type {(target: any, classname: string) => boolean}
 */
const is = (target, classname) => {
    console.warn("This function is not supported starting with 1.1.");
    return typeof target === classname; 
};
/**
 * @deprecated This function is not supported starting with 1.1.
 * @type {(millisecond: number) => Promise<>}
 */
const wait = millisecond => { 
    console.warn("This function is not supported starting with 1.1.")
    return new Promise(code => setTimeout(code, millisecond)); 
}
/**
 * @deprecated This function is not supported starting with 1.1.
 * @type {{
 * (parent: any[], child: any) => number
 * (parent: HTMLElement, child: HTMLElement) => number
 * }}
 */
const getIndex = (parent, child) => { 
    console.warn("This function is not supported starting with 1.1.")
    return parent.nodeName != null ? Array.prototype.indexOf.call(parent.children, child) : Array.prototype.indexOf.call(parent, child); 
};
/** 
 * @type {{
* (selector: `!${string}`) => NodeListOf<HTMLElement>;
* (selector: string) => HTMLElement
* (selector: HTMLElement) => HTMLElement
* }}
*/
const scan = selector => { return is(selector, "string") ? selector.in("!") ? document.querySelectorAll(selector.split("!")[1]) : document.querySelector(selector) : selector; }
/**
 * @type {{
* (selector: `!${string}`) => DomExpert[]
* (selector: string) => DomExpert
* (selector: HTMLElement) => DomExpert
* }}
*/
const snipe = selector => {
   const temp = (is(selector, "string") && selector[0] == "!") ? [] : $(scan(selector));
   if (is(temp, "object")) for (let i = 0; i < scan(selector).length; i++) temp.push($(scan(selector)[i]));
   return temp;
}
/**
 * @type {(jhpath: string) => void}
 * @throws {SecurityError}
 */
const loading  = jhpath => {
    const REQUEST = new XMLHttpRequest();
    if (jhpath.indexOf("http") != -1) throw new SecurityError("loading 함수로 다른 웹사이트의 htm.js를 로딩할 수 없습니다.");
    else {
        REQUEST.open('GET', `${jhpath}.htm.js`);
        REQUEST.send();
        REQUEST.onreadystatechange = (e => { (e.target.readyState == 4) ? eval(REQUEST.response) : null});
    }
}