/*
js에서 값의 변화를 관측하는 LiveData를 비롯한 여러 기능들을 사용할 수 있게 해줍니다.
작성자: 환류상
 */
const LiveData = class {
    #data;
    #observer;
    #allowed;
    /**
     * @type {(observer: Function) => LiveData}
     */
    registObserver = observer => {
        this.#observer = observer;
        return this;
    }
    /**
     * @deprecated This can cause unintended behavior.
     * @type {() => void}
     */
    dispatchObserver = () => this.#observer();
    /**
     * @type {(data: Any) => LiveData}
     */
    set = data => {
        if (this.#allowed && this.#allowed.name.toLocaleLowerCase() !== (Array.isArray(data) ? "array" : typeof data)) throw new TypeError(`invalid type of data. Data must be of type ${this.#allowed.name}.`);
        const isChanged = JSON.stringify(data) !== JSON.stringify(this.#data);
        this.#data = data;
        if (isChanged && typeof this.#observer == "function") this.#observer();
    }
    /**
     * @type {() => Any}
     */
    get = () => (Array.isArray(this.#data)) ? [...this.#data] : (typeof this.#data == "object") ? Object.assign({}, this.#data) : this.#data;
    /**
     * @type {(data: Any, allowed: Type) => LiveData}
     */
    constructor(data, allowed) {
        this.#data = data;
        this.#allowed = allowed;
    }
}
JSON.unlivedata = json => {
    let data = {};
    for (let key of Object.keys(json)) data[key] = (json[key] instanceof LiveData) ? json[key].get() : json[key];
    return data;
}
Array.unlivedata = array => {
    let data = []
    for (let value of array) data.push((value instanceof LiveData) ? value.get() : value);
    return data;
}
