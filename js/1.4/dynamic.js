/*
js로 html 요소를 동적으로 더 쉽게 다룰 수 있게 해 줍니다.
작성자: 환류상
 */
console.log("%cFrom this version, we do not recommend using it for security reasons.\nWe recommend using a 'Rose' model that deals with models 2.0 or higher.", "color: red");
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
    static #animation = {
        card: async fragment => {
            if (snipe(fragment.#view).node.innerHTML != "") {
                snipe(fragment.#view).node.animate([{transform: 'rotateY(0deg)', opacity: '1'}, {transform: 'rotateY(180deg)', opacity: '0'}], {duration: fragment.#animationExcuteTime * 500,})
                await new Promise(code => setTimeout(code, fragment.#animationExcuteTime * 450));
                snipe(fragment.#view).reset(fragment.#domlist);
                snipe(fragment.#view).node.animate([{transform: 'rotateY(180deg)', opacity: '0'}, {transform: 'rotateY(360deg)', opacity: '1'}], {duration: fragment.#animationExcuteTime * 500,})
            } else snipe(fragment.#view).reset(fragment.#domlist);
            if (typeof fragment.#action == "function") fragment.#action();
        },
        fade: async fragment => {
            if (snipe(fragment.#view).node.innerHTML != "") {
                snipe(fragment.#view).node.animate([{opacity: '1'}, {opacity: '0'}], {duration: fragment.#animationExcuteTime * 500,})
                await new Promise(code => setTimeout(code, fragment.#animationExcuteTime * 400));
                snipe(fragment.#view).reset(fragment.#domlist);
                snipe(fragment.#view).node.animate([{opacity: '0'}, {opacity: '1'}], {duration: fragment.#animationExcuteTime * 500,})
            } else snipe(fragment.#view).reset(fragment.#domlist);
            if (typeof fragment.#action == "function") fragment.#action();
        },
        swip: async fragment => {
            if (snipe(fragment.#view).node.innerHTML != "") {
                scan("html").style.overflowX = "hidden";
                snipe(fragment.#view).node.animate([{transform: 'translateX(0px)'}, {transform: 'translateX(100%)'}], {duration: fragment.#animationExcuteTime * 450,})
                await new Promise(code => setTimeout(code, fragment.#animationExcuteTime * 400));
                snipe(fragment.#view).reset(fragment.#domlist);
                snipe(fragment.#view).node.animate([{transform: 'translateX(-100%)'}, {transform: 'translateX(0px)'}], {duration: fragment.#animationExcuteTime * 550,})
                scan("html").style.overflowX = null;
            } else snipe(fragment.#view).reset(fragment.#domlist);
            if (typeof fragment.#action == "function") fragment.#action();
        }
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
     * @type {(animation: FragAnimation, second: Number) => Fragment}
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
     * @type {(view: String, ...domlist: Dom | Dom[]) => Fragment}
     */
    constructor(view, ...domlist) {
        this.#rid = view;
        this.#view = `fragment[rid=${view}]`;
        this.#domlist = domlist;
    }
}
const FragmentBox = class {
    static #launchedInfo = {
        target: null,
        fragments: {},
        router: {}
    };

    static #syncActivate = (fragment, arg) => {
        if (this.#launchedInfo.target == fragment.rid) return;
        if (!scan(`fragment[rid=${fragment.rid}]`)) {
            snipe("fragmentbox").add($("fragment", {rid: fragment.rid}));
            fragment.launch(arg);
            this.#launchedInfo.fragments[fragment.rid] = fragment;
        }
        scan("!fragmentbox fragment").forEach(node => node.style.display = "none");
        scan(`fragment[rid=${fragment.rid}]`).style.display = null;
    }

    static toggle = (fragment, arg, alwayRefresh = false) => {
        this.#syncActivate(fragment, arg);
        if (alwayRefresh || this.#launchedInfo.target == fragment.rid) {
            fragment.launch(arg);
            this.#launchedInfo.fragments[fragment.rid] = fragment;
        }
        snipe("router").reset(this.#launchedInfo.router[fragment.rid]);
        this.#launchedInfo.target = fragment.rid;
    };
    static setRouter = (rid, domlist) => this.#launchedInfo.router[rid] = domlist;
    static refresh = () => this.#launchedInfo.fragments[this.#launchedInfo.target].launch();
}
const FragAnimation = class {
    static card = "card";
    static fade = "fade";
    static swip = "swip";
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
