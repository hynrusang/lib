/*
js로 html 요소를 동적으로 더 쉽게 다룰 수 있게 해 줍니다.
작성자: 환류상
 */
console.warn("From this version, I will no longer update the 1.X version.\nI recommend using a 'Rose' model that deals with models 2.0 or higher.");
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
    #rid;
    #view;
    #domlist;
    #action;
    #swipAnimation;
    #animationExcuteTime;

    /**
     * @type {(arg: any) => Fragment}
     */
    launch = arg => {
        if (this.#swipAnimation != null) Fragment.#animation[this.#swipAnimation](this);
        else {
            snipe(this.#view).reset(this.#domlist)
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
     * @type {(animation: string, second: Number) => Fragment}
     */
    registAnimation = (animation, second) => {
        this.#swipAnimation = animation;
        this.#animationExcuteTime = second;
        return this;
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
const FragMutation = class {
    static #launchedInfo = {
        target: null,
        fragments: {},
        router: {}
    };

    /**
     * @type {(fragment: Fragment, arg: any, alwayRefresh: boolean) => void}
     */
    static mutate = (fragment, arg, alwayRefresh = false) => {
        const router = scan("router");
        if (!scan(`fragment[rid=${fragment.rid}]`)) {
            snipe("fragmentbox").add($("fragment", {rid: fragment.rid}));
            fragment.launch(arg);
            this.#launchedInfo.fragments[fragment.rid] = fragment;
        } else if (alwayRefresh || this.#launchedInfo.target == fragment.rid) {
            fragment.launch(arg);
            this.#launchedInfo.fragments[fragment.rid] = fragment;
        };
        if (this.#launchedInfo.target != fragment.rid) {
            scan("!fragmentbox fragment").forEach(node => node.style.display = "none");
            scan(`fragment[rid=${fragment.rid}]`).style.display = null;
            if (router) snipe(router).reset(this.#launchedInfo.router[fragment.rid]);
            this.#launchedInfo.target = fragment.rid;
        }
    };
    /**
     * @type {(rid: String, domlist: FragDom[]) => void}
     */
    static setRouter = (rid, domlist) => {
        this.#launchedInfo.router[rid] = domlist;
        if (this.#launchedInfo.target == rid) snipe("router").reset(domlist)
    }
    /**
     * @type {() => void}
     */
    static refresh = () => this.#launchedInfo.fragments[this.#launchedInfo.target].launch();
}
