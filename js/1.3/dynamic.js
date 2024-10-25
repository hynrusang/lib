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
        if (0 < dom.length && dom[0]) {
            for (let pdom of dom) {
                if (Array.isArray(pdom)) {
                    for (let cdom of pdom) if (cdom) this.#node.appendChild(cdom.node);
                } else if (pdom) this.#node.appendChild(pdom.node);
            }
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
    static #animation = class {
        static #applyAnimation = async (fragment, animationIn, animationOut) => {
            const view = snipe(fragment.#view);
            if (view.node.innerHTML != "") {
                await view.node.animate(animationIn, {duration: fragment.#animationExcuteTime * 0.5}).finished;
                view.reset(fragment.#domlist);
                view.node.animate(animationOut, {duration: fragment.#animationExcuteTime * 0.5});
            } else view.reset(fragment.#domlist);
            if (typeof fragment.#action === "function") fragment.#action();
        };
        static card = async fragment => await this.#applyAnimation(fragment, [
            {transform: 'rotateY(0deg)', opacity: '1'}, 
            {transform: 'rotateY(180deg)', opacity: '0'}
        ], [
            {transform: 'rotateY(180deg)', opacity: '0'}, 
            {transform: 'rotateY(360deg)', opacity: '1'}
        ]);
        static fade = async fragment => await this.#applyAnimation(fragment, [
            {opacity: '1'}, 
            {opacity: '0'}
        ], [
            {opacity: '0'}, 
            {opacity: '1'}
        ]);
        static swip = async fragment => await this.#applyAnimation(fragment, [
            { left: '0px', position: 'relative' }, 
            { left: '100vw', position: 'relative' }
        ], [
            { left: '-100vw', position: 'relative' }, 
            { left: '0px', position: 'relative' }
        ]);
    }
    static #launchedFragment;
    #rid;
    #view;
    #domlist;
    #action;
    #swipAnimation;
    #animationExcuteTime;

    /**
     * @deprecated This method is not supported starting with 1.4.
     * @type {(arg: any) => Fragment}
     */
    static refreshFragment = arg => {
        console.warn("This method is not supported starting with 1.4.");
        this.#launchedFragment.launch(arg);
    }
    /**
     * @type {(arg: any) => Fragment}
     */
    launch = arg => {
        if (this.#swipAnimation != null) Fragment.#animation[this.#swipAnimation](this);
        else {
            snipe(this.#view).reset(this.#domlist)
            if (typeof this.#action == "function") this.#action(arg);
        }
        Fragment.#launchedFragment = this;
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
     * @type {(animation: FragAnimation, milliSecond: Number) => Fragment}
     */
    registAnimation = (animation, milliSecond) => {
        this.#swipAnimation = animation;
        this.#animationExcuteTime = milliSecond;
        return this;
    }

    /**
     * @deprecated This method is not supported starting with 1.4.
     * @param {Fragment} fragment 
     */
    static set launchedFragment(fragment) {
        console.warn("This method is not supported starting with 1.4.");
        this.#launchedFragment = fragment;
    }

    /**
     * @type {() => String}
     */
    get rid() {
        return this.#rid;
    }
    /**
     * @type {(view: String, ...domlist: FragDom | FragDom[]) => Fragment}
     */
    constructor(view, ...domlist) {
        this.#rid = view;
        this.#view = `fragment[rid=${view}]`;
        this.#domlist = domlist;
    }
}
/**
 * @deprecated This class is not supported starting with 1.4.
 */
const FragAnimation = class {
    static get card() {
        console.warn("This class is not supported starting with 1.4.");
        return "card";
    }
    static get fade() {
        console.warn("This class is not supported starting with 1.4.");
        return "fade";
    }
    static get swip() {
        console.warn("This class is not supported starting with 1.4.");
        return "swip";
    }
}
