﻿/*
js로 html 요소를 동적으로 더 쉽게 다룰 수 있게 해 줍니다.
작성자: 환류상

업데이트 내역
1. function help: 현재 dynamic 버전에서 지원하는 기능들을 console.dir로 출력
1. class FragMutation 도입으로 기존에는 하지 못했던 다양한 기능을 손쉽게 제공함.
2. FragMutation.mutate: Fragment의 rid를 기준으로, 현재 rid를 가진 Fragment 내의 작업 내용을 그대로 가지고 새로운 rid를 가진 Fragment에서 추가적인 작업 수행 가능
3. FragMutation.setRouter: FragMutation.mutate가 실행될 때, 동일한 rid를 가진 다수의 Fragment를 Routing하는 Router 배열 등록 가능
4. FragMutation.refresh: 현재 Fragment를 단순히 새로고침함

삭제 내역
1. class FragAnimation: merged by function help
1. Fragment.refreshFragment: merged by FragMutation.refresh
2. Fragment.launchedFragment: merged by FragMutation.mutate
 */

/**
 * @description This class will be renamed to `DocumentContainer` starting from version 2.1.
 */
const FragDom = class {
    #node;

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
     * @deprecated This method is not supported starting with 2.1.
     * @type {(num: number) => FragDom}
     */
    remove = num => {
        console.warn("This method is not supported starting with 2.1.");
        this.#node.removeChild(this.children(num).node);
        return this;
    }
    /**
     * @deprecated This method is not supported starting with 2.1.
     * @type {(num: number) => FragDom}
     */
    children = num => {
        console.warn("This method is not supported starting with 2.1.");
        return this.#node.children[num] ? new FragDom(this.#node.children[num]) : null;
    }
    /**
     * @type {(...dom: FragDom) => FragDom}
     */
    add = (...dom) => {
        for (let pdom of dom.flat()) pdom ? this.#node.appendChild(pdom.node) : null;
        return this;
    }
    /**
     * @type {(...dom?: FragDom) => FragDom}
     */
    reset = (...dom) => {
        this.#node.innerHTML = "";
        this.add(...dom);
        return this;
    }

    get node() {
        return this.#node;
    }
    /**
     * @type {(node: string | HTMLElement, additional: Object?) => FragDom}
     */
    constructor(node, additional) {
        console.warn("FragDom will be renamed to `DocumentContainer` in version 2.1.")

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
            snipe(this.#view).reset(...this.#domlist)
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

export {$, scan, snipe, help, Fragment, FragMutation}
