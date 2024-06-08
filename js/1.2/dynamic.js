/*
js로 html 요소를 동적으로 더 쉽게 다룰 수 있게 해 줍니다.
작성자: 환류상
 */
const FragDom = class {
    #node;
    /**
     * @type {() => HTMLElement}
     */
    get node() {
        return this.#node;
    }
    /**
     * @type {(additional: Object) => FragDom}
     */
    set = additional => {
        if (typeof additional === 'object') {
            for (const [key, value] of Object.entries(additional)) {
                if (["innerHTML", "html"].includes(key)) this.#node.innerHTML = value
                else if (["innerText", "text"].includes(key)) this.#node.innerText = value
                else if (!key.indexOf("on") || key == "async" || (this.#node.nodeName == "TEXTAREA" && key == "value")) this.#node[key] = value
                else if (typeof value != "undefined") this.#node.setAttribute(key, value);
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
                for (let cdom of pdom) if (cdom) this.#node.appendChild(cdom.node);
            } else if (pdom) this.#node.appendChild(pdom.node);
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
     * @type {(node: string | HTMLElement, additional: Object?) => FragDom}
     */
    constructor(node, additional) {
        if (typeof node !== "string" && !node instanceof HTMLElement) throw new TypeError("node is not instanceof HTMLElement. node must be instanceof HTMLElement");
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
    if (Array.isArray(temp)) for (let i = 0; i < scan(selector).length; i++) temp.push($(scan(selector)[i]));
    return temp;
}
const Fragment = class {
    #view;
    #domlist;
    #action;
    #swipAnimation;
    #animationExcuteTime;
    /**
     * @deprecated This getter is not supported starting with 1.3.
     * @type {() => Function}
     */
    get _action() {
        console.warn("This getter is not supported starting with 1.3.");
        return this.#action;
    }
    /**
     * @deprecated This getter is not supported starting with 1.3.
     * @type {() => HTMLElement}
     */
    get _view() {
        console.warn("This getter is not supported starting with 1.3.");
        return this.#view;
    }
    /**
     * @deprecated This getter is not supported starting with 1.3.
     * @type {() => FragDom[]}
     */
    get domlist() {
        console.warn("This getter is not supported starting with 1.3.");
        return this.#domlist;
    }
    /**
     * @type {() => Fragment}
     */
    launch = () => {
        if (this.#swipAnimation != null) this.#swipAnimation(this, this.#animationExcuteTime);
        else {
            snipe(this.#view).reset(this.#domlist)
            if (typeof this.#action == "function") this.#action();
        }
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
     * @type {(animation: FragAnimation, millisecond: Number) => Fragment}
     */
    registAnimation = (animation, millisecond) => {
        this.#swipAnimation = animation;
        this.#animationExcuteTime = millisecond;
        return this;
    }
    /**
     * @type {(view: String, ...domlist: FragDom | FragDom[]) => Fragment}
     */
    constructor(view, ...domlist) {
        this.#view = `fragment[rid=${view}]`;
        this.#domlist = domlist;
    }
}
const FragAnimation = class {
    static #applyAnimation = async (_fragment, _millisecond, animationIn, animationOut) => {
        const view = snipe(_fragment._view);
        if (view.node.innerHTML != "") {
            await view.node.animate(animationIn, {duration: _millisecond * 0.5}).finished;
            view.reset(_fragment._domlist);
            view.node.animate(animationOut, {duration: _millisecond * 0.5});
        } else view.reset(_fragment._domlist);
        if (typeof _fragment._action === "function") _fragment._action();
    };
    static card = async (_fragment, _millisecond) => await this.#applyAnimation(_fragment, _millisecond, [
        {transform: 'rotateY(0deg)', opacity: '1'}, 
        {transform: 'rotateY(180deg)', opacity: '0'}
    ], [
        {transform: 'rotateY(180deg)', opacity: '0'}, 
        {transform: 'rotateY(360deg)', opacity: '1'}
    ]);
    static fade = async (_fragment, _millisecond) => await this.#applyAnimation(_fragment, _millisecond, [
        {opacity: '1'}, 
        {opacity: '0'}
    ], [
        {opacity: '0'}, 
        {opacity: '1'}
    ]);
    static swip = async (_fragment, _millisecond) => await this.#applyAnimation(_fragment, _millisecond, [
        { left: '0px', position: 'relative' }, 
        { left: '100vw', position: 'relative' }
    ], [
        { left: '-100vw', position: 'relative' }, 
        { left: '0px', position: 'relative' }
    ]);
}
