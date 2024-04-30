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
     * @deprecated This method is not supported starting with 1.2. use LiveData.value setter instead.
     * @type {(data: Any) => LiveData}
     */
    set = data => { 
        console.warn("This method is not supported starting with 1.2.0.\nuse LiveData.value setter instead.");
        this.value = data;
        return this;
    }
    /**
     * @deprecated This method is not supported starting with 1.2. use LiveData.value getter instead.
     * @type {() => Any}
     */
    get = () => {
        console.warn("This method is not supported starting with 1.2.0.\nuse LiveData.value getter instead.");
        return this.value;
    }
    set value(data) {
        if (this.#allowed && this.#allowed.name.toLocaleLowerCase() !== (Array.isArray(data) ? "array" : typeof data)) throw new TypeError(`invalid type of data. Data must be of type ${this.#allowed.name}.`);
        const isChanged = JSON.stringify(data) !== JSON.stringify(this.#data);
        this.#data = data;
        if (isChanged && typeof this.#observer == "function") this.#observer();
    }
    get value() {
        return (Array.isArray(this.#data)) ? [...this.#data] : (typeof this.#data == "object") ? Object.assign({}, this.#data) : this.#data;
    }
    /**
     * @type {(data: Any, allowed: Type) => LiveData}
     */
    constructor(data, allowed) {
        this.#data = data;
        this.#allowed = allowed;
    }
}
const LiveManager = class {
    #editable;
    #livedataObject;
    value = (id, data) => (typeof data !== "undefined") ? this.#livedataObject[id].value = data : this.#livedataObject[id].value;
    get id() {
        if (!this.#editable) throw new SyntaxError(`This LiveDataManager cannot be accessed or modified externally.`)
        return this.#livedataObject;
    }
    toArray = () => Object.values(this.#livedataObject).map(inner => inner.value);
    toObject = () => Object.entries(this.#livedataObject).reduce((obj, [key, value]) => {
        obj[key] = value.value;
        return obj;
      }, {});
    /**
     * @type {(livedataObject: Object, editable?: boolean) => LiveDataManager}
     */
    constructor(livedataObject, editable = true) {
        if ("object" !== (Array.isArray(livedataObject) ? "array" : typeof livedataObject)) throw new TypeError(`invalid type of livedataObject. livedataObject must be of type Object. (livedataObject: ${Array.isArray(livedataObject) ? "array" : typeof livedataObject})`);
        this.#editable = editable;
        this.#livedataObject = Object.entries(livedataObject).reduce((obj, [key, value]) => { 
            if (!(value instanceof LiveData)) throw new TypeError(`invalid type of ${key}'s value. ${key}'s value must be of instance LiveData`)
            else obj[key] = value;
            return obj;
        }, {});
    }
}
/**
 * @deprecated This prototype is not supported starting with 1.2. use LiveDataManager.toObject instead.
 * @type {(json: Object) => Object}
 */
JSON.unlivedata = json => {
    console.warn("This prototype is not supported starting with 1.2.\nuse LiveDataManager.toObject instead.");
    let data = {};
    for (let key of Object.keys(json)) data[key] = (json[key] instanceof LiveData) ? json[key].value : json[key];
    return data;
}
/**
 * @deprecated This prototype is not supported starting with 1.2. use LiveDataManager.toArray instead.
 * @type {(json: Object) => Object}
 */
Array.unlivedata = array => {
    console.warn("This prototype is not supported starting with 1.2.\nuse LiveDataManager.toArray instead.");
    let data = []
    for (let inner of array) data.push((inner instanceof LiveData) ? inner.value : inner);
    return data;
}
