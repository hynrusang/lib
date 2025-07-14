/*
js로 html 요소를 동적으로 더 쉽게 다룰 수 있게 해 줍니다.
작성자: 환류상

업데이트 내역
1. FragMutation.refresh()를 할 때, 현재 Fragment 객체의 모든 스크롤 정보를 같이 동기화.
2. 내부 FragDom 구조체 최적화 (이름 수정 FragDom => DocumentContainer)
 */
const DocumentContainer = class {
    #node;

    /**
     * @type {(...dom: DocumentContainer) => DocumentContainer}
     * @description Appends one or more DocumentContainer elements to the current node.
     */
    add = (...dom) => {
        for (const pdom of dom) { 
            if (Array.isArray(pdom)) this.add(...pdom);
            else if (pdom instanceof DocumentContainer) this.#node.appendChild(pdom.node);
            else if (pdom instanceof HTMLElement) this.#node.appendChild(pdom);
        }
        return this;
    }

    /**
     * @type {(additional: Object) => DocumentContainer}
     * @description Sets various properties or attributes of the DOM element.
     */
    set = additional => {
        if (typeof additional !== 'object') throw new TypeError("set() requires an object.");

        for (const [key, value] of Object.entries(additional)) {
            if (["innerHTML", "html"].includes(key)) this.#node.innerHTML = value
            else if (["innerText", "text"].includes(key)) this.#node.innerText = value
            else if (!key.indexOf("on") || key == "async" || (this.#node.nodeName == "TEXTAREA" && key == "value")) this.#node[key] = value
            else if (typeof value != "undefined") this.#node.setAttribute(key, value);
        }
        return this;
    };

    /**
     * @type {(...dom?: DocumentContainer) => DocumentContainer}
     * @description Clears the current node’s children and appends new ones.
     */
    reset = (...dom) => {
        this.#node.innerHTML = "";
        return this.add(...dom);;
    }

    /**
     * @type {() => HTMLElement}
     * @description Returns the underlying native HTMLElement associated with this DocumentContainer instance.
     */
    get node() {
        return this.#node;
    }

    /**
     * @type {(node: string | HTMLElement, additional: Object?) => DocumentContainer}
     */
    constructor(node, additional) {
        if (typeof node !== "string" && !node instanceof HTMLElement) throw new TypeError("node is not instanceof HTMLElement. node must be instanceof HTMLElement");
        this.#node = (typeof node === "string") ? document.createElement(node) : node;
        if (typeof additional !== 'undefined') this.set(additional);
    }
}

/**
 * @type {(node: string | HTMLElement, additional?: Object) => DocumentContainer}
 */
const $ = (node, additional) => new DocumentContainer(node, additional);

/** 
 * @type {{
 * (selector: `!${string}`) => NodeListOf<HTMLElement>;
 * (selector: string) => HTMLElement
 * (selector: HTMLElement) => HTMLElement
 * }}
 */
const scan = selector => (typeof selector == "string") 
    ? (selector[0] == "!") 
        ? document.querySelectorAll(selector.split("!")[1]) 
        : document.querySelector(selector) 
    : selector;

/**
 * @type {{
 * (selector: `!${string}`) => DocumentContainer[]
 * (selector: string) => DocumentContainer
 * (selector: HTMLElement) => DocumentContainer
 * }}
 */
const snipe = selector => {
    if (typeof selector === "string" && selector.startsWith("!")) return Array.from(scan(selector)).map(el => $(el));
    return $(scan(selector));
}
/**
 * @type {() => null}
 */
const help = () => {
    console.log("support animation: [card, fade, swip]");
    console.log("support function: [$(node, additional), scan(selector), snipe(selector)]");
    console.log("support class: [Fragment, FragMutation]");
    console.log("support Fragment methods: [.launch(arg), .registAction(action), .registAnimation(animation, second)]");
    console.log("support Fragment getter: [.rid]")
    console.log("support FragMutation methods: [.mutate(fragment, arg, alwayRefresh = false), .setRouter(rid, domlist), .refresh()]")
    console.log("support FragMutation getter: []")
}

const Fragment = class {
    static #animation = class {
        static async #applyAnimation(fragment, animationIn, animationOut) {
            const view = snipe(fragment.#view);
            const animationDuration = { duration: fragment.#animationExcuteTime * 0.5 };

            if (view.node.innerHTML) {
                await view.node.animate(animationIn, animationDuration).finished;
                view.reset(fragment.#domlist);
                view.node.animate(animationOut, animationDuration);
            } else view.reset(fragment.#domlist);
            
            if (typeof fragment.#action === 'function') fragment.#action();
        }

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
        if (this.#swipAnimation) Fragment.#animation[this.#swipAnimation](this);
        else {
            snipe(this.#view).reset(...this.#domlist)
            if (typeof this.#action == "function") this.#action(arg);
        }
        return this;
    }

    /**
     * @type {(action: Function) => Fragment}
     */
    registAction = action => (this.#action = action, this);

    /**
     * @type {(animation: string, second: Number) => Fragment}
     */
    registAnimation = (animation, second) => (this.#swipAnimation = animation, this.#animationExcuteTime = second, this);

    get rid() {
        return this.#rid;
    }

    /**
     * @type {(view: String, ...domlist: DocumentContainer | DocumentContainer[]) => Fragment}
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
            snipe("fragmentbox").add($("fragment", { rid: fragment.rid }));
            fragment.launch(arg);
            this.#launchedInfo.fragments[fragment.rid] = fragment;
        } else if (alwayRefresh || this.#launchedInfo.target == fragment.rid) {
            fragment.launch(arg);
            this.#launchedInfo.fragments[fragment.rid] = fragment;
        };

        if (this.#launchedInfo.target != fragment.rid) {
            scan("!fragmentbox fragment").forEach(el => el.style.display = "none");
            scan(`fragment[rid=${fragment.rid}]`).style.display = null;

            if (router) snipe(router).reset(this.#launchedInfo.router[fragment.rid]);
            this.#launchedInfo.target = fragment.rid;
        }
    };

    /**
     * @type {(rid: String, domlist: DocumentContainer[]) => void}
     */
    static setRouter = (rid, domlist) => {
        this.#launchedInfo.router[rid] = domlist;
        if (this.#launchedInfo.target == rid) snipe("router").reset(domlist)
    }

    /**
     * @type {() => void}
     */
    static refresh = () => {
        const currentRid = this.#launchedInfo.target;
        const elements = Array.from(scan(`!fragment[rid='${currentRid}'] *`));
        const scrolls = elements.map(el => el.scrollTop);

        this.#launchedInfo.fragments[currentRid].launch();
        elements.forEach((el, i) => el.scrollTop = scrolls[i]);
    }
}

export {$, scan, snipe, help, Fragment, FragMutation}
