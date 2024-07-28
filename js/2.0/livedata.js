/*
js에서 값의 변화를 관측하는 LiveData를 비롯한 여러 기능들을 사용할 수 있게 해줍니다.
작성자: 환류상
 */
const LiveData = class {
    #data;
    #type;
    #observer;
    set value(data) {
        if (this.#type && this.#type.name.toLocaleLowerCase() !== (Array.isArray(data) ? "array" : typeof data)) throw new TypeError(`invalid type of data. Data must be of type ${this.#type.name}.`);
        const isChanged = JSON.stringify(data) !== JSON.stringify(this.#data);
        this.#data = data;
        if (isChanged && typeof this.#observer == "function") this.#observer();
    }
    get value() {
        return (typeof this.#data == "object") ? JSON.parse(JSON.stringify(this.#data)) : this.#data;
    }
    /**
     * @type {(data: Any, dataset: object) => LiveData}
     */
    constructor(data, dataset) {
        this.#data = data;
        if (dataset) {
            this.#type = dataset.type;
            this.#observer = dataset.observer;
        }
    }
}
/**
 * @type {(data: Any, dataset: object) => LiveData}
 */
const $ = (data, dataset) => new LiveData(data, dataset);
const LiveManager = class {
    #editable;
    #livedataObject;
    value = (id, data) => {
        if (typeof data !== "undefined") {
            const isChanged = JSON.stringify(this.#livedataObject[id].value) !== JSON.stringify(data);
            this.#livedataObject[id].value = data;
            return isChanged;
        } else return this.#livedataObject[id].value;
    }
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

export { $, LiveManager }