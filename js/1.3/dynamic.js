/*
js로 html 요소를 동적으로 더 쉽게 다룰 수 있게 해 줍니다.
작성자: 환류상
 */
const Dom = class {
    #node;
    /**
     * @type {() => HTMLElement}
     */
    get node() {
        return this.#node;
    }
    /**
     * @type {(additional: Object) => Dom}
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
                for (let cdom of pdom) if (cdom) this.#node.appendChild(cdom.node);
            } else if (pdom) this.#node.appendChild(pdom.node);
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
     * @type {(node: string | HTMLElement, additional: Object?) => Dom}
     */
    constructor(node, additional) {
        if (typeof node !== "string" && !node instanceof HTMLElement) throw new TypeError("node is not instanceof HTMLElement. node must be instanceof HTMLElement");
        this.#node = (typeof node === "string") ? document.createElement(node) : node;
        if (typeof additional !== 'undefined') this.set(additional);
    }
}
const Fragment = class {
    #rid;
    #view;
    #fragment;
    #action;
    #swipAnimation;
    #animationExcuteTime;
    /**
     * @type {() => Function}
     */
    get _action() {
        return this.#action;
    }
    /**
     * @type {() => HTMLElement}
     */
    get _view() {
        return this.#view;
    }
    /**
     * @deprecated This getter is not supported starting with 1.4.0. Use Fragment.launch() instead.
     * @type {() => Dom[]}
     */
    get fragment() {
        return this.#fragment;
    }
    /**
     * @type {() => String}
     */
    get rid() {
        return this.#rid;
    }

    /**
     * @type {(arg: any) => Fragment}
     */
    launch = arg => {
        if (this.#swipAnimation != null) this.#swipAnimation(this, this.#animationExcuteTime);
        else {
            snipe(this.#view).reset(this.#fragment)
            if (typeof this.#action == "function") this.#action(arg);
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
     * @type {(animation: FragAnimation, second: Number) => Fragment}
     */
    registAnimation = (animation, second) => {
        this.#swipAnimation = animation;
        this.#animationExcuteTime = second;
        return this;
    }
    /**
     * @type {(view: String, ...fragment: Dom | Dom[]) => Fragment}
     */
    constructor(view, ...fragment) {
        this.#rid = view;
        this.#view = `fragment[rid=${view}]`;
        this.#fragment = fragment;
    }
}
const FragAnimation = class {
    static card = async (_fragment, _second) => {
        if (snipe(_fragment._view).node.innerHTML != "") {
            snipe(_fragment._view).node.animate([{transform: 'rotateY(0deg)', opacity: '1'}, {transform: 'rotateY(180deg)', opacity: '0'}], {duration: _second * 500,})
            await new Promise(code => setTimeout(code, _second * 450));
            snipe(_fragment._view).reset(_fragment.fragment);
            snipe(_fragment._view).node.animate([{transform: 'rotateY(180deg)', opacity: '0'}, {transform: 'rotateY(360deg)', opacity: '1'}], {duration: _second * 500,})
        } else snipe(_fragment._view).reset(_fragment.fragment);
        if (typeof _fragment._action == "function") _fragment._action();
    }
    static fade = async (_fragment, _second) => {
        if (snipe(_fragment._view).node.innerHTML != "") {
            snipe(_fragment._view).node.animate([{opacity: '1'}, {opacity: '0'}], {duration: _second * 500,})
            await new Promise(code => setTimeout(code, _second * 400));
            snipe(_fragment._view).reset(_fragment.fragment);
            snipe(_fragment._view).node.animate([{opacity: '0'}, {opacity: '1'}], {duration: _second * 500,})
        } else snipe(_fragment._view).reset(_fragment.fragment);
        if (typeof _fragment._action == "function") _fragment._action();
    }
    static swip = async (_fragment, _second) => {
        if (snipe(_fragment._view).node.innerHTML != "") {
            scan("html").style.overflowX = "hidden";
            snipe(_fragment._view).node.animate([{transform: 'translateX(0px)'}, {transform: 'translateX(100%)'}], {duration: _second * 450,})
            await new Promise(code => setTimeout(code, _second * 400));
            snipe(_fragment._view).reset(_fragment.fragment);
            snipe(_fragment._view).node.animate([{transform: 'translateX(-100%)'}, {transform: 'translateX(0px)'}], {duration: _second * 550,})
            scan("html").style.overflowX = null;
        } else snipe(_fragment._view).reset(_fragment.fragment);
        if (typeof _fragment._action == "function") _fragment._action();
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
    if (Array.isArray(temp)) for (let i = 0; i < scan(selector).length; i++) temp.push($(scan(selector)[i]));
    return temp;
}
