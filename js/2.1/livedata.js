/*
js에서 값의 변화를 관측하는 LiveData를 비롯한 여러 기능들을 사용할 수 있게 해줍니다.
작성자: 환류상

업데이트 내역
1. LiveData 및 LiveManager 구조 효율적 리펙토링
 */
const LiveData = class {
    #data;
    #type;
    #observer;

    set value(data) {
        if (this.#type && this.#type.name.toLocaleLowerCase() !== (Array.isArray(data) ? "array" : typeof data)) throw new TypeError(`invalid type of data. Data must be of type ${this.#type.name}.`);
        
        const isChanged = data.equals 
            ? data.equals(this.#data) 
            : JSON.stringify(data) !== JSON.stringify(this.#data);

        this.#data = data;
        if (isChanged && typeof this.#observer == "function") this.#observer();
    }

    get value() {
        return (typeof this.#data == "object") 
        ? JSON.parse(JSON.stringify(this.#data)) 
        : this.#data;
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

/**
 * @type {() => null}
 */
const help = () => {
    console.log("support liveable type: [string, number, bigint, boolean, array, object]");
    console.log("support function: [$(data, dataset)]");
    console.log("support class: [LiveManager]");
    console.log("support LiveManager methods: [.value(id), .value(id, data), .toArray(), .toObject()]");
    console.log("support LiveManager getter: [.id]")
}

const LiveManager = class {
    #editable;
    #livedataObject;

    value = (id, data) => {
        if (typeof data !== "undefined") {
            const isChanged = data.equals 
                ? data.equals(this.#livedataObject[id].value) 
                : JSON.stringify(this.#livedataObject[id].value) !== JSON.stringify(data);
            
            this.#livedataObject[id].value = data;
            return isChanged;
        } else return this.#livedataObject[id].value;
    }

    toArray = () => Object.values(this.#livedataObject).map(inner => inner.value);
    toObject = () => Object.fromEntries(
        Object.entries(this.#livedataObject).map(([key, livedata]) => [key, livedata.value])
    );
    
    get id() {
        if (!this.#editable) throw new SyntaxError(`This LiveDataManager cannot be externally accessed or modified.`)
        return this.#livedataObject;
    }

    /**
     * @type {(livedataObject: Object, editable?: boolean) => LiveDataManager}
     */
    constructor(livedataObject, editable = true) {
        if (typeof livedataObject !== "object" || Array.isArray(livedataObject)) throw new TypeError(`invalid type of livedataObject. Must be an Object.`);

        this.#editable = editable;
        this.#livedataObject = Object.fromEntries(
            Object.entries(livedataObject).map(([key, value]) => {
                if (!value instanceof LiveData) throw new TypeError(`invalid type of ${key}. Must be instance of LiveData.`);
                return [key, value];
            })
        )
    }
}

export { $, help, LiveManager }
