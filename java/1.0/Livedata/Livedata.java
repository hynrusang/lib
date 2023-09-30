package Livedata;

public class Livedata<T> {
    private T value;
    private Observer<T> observer;

    public T getValue() { return this.value; }
    public boolean setValue(T value) {
        if (this.value.equals(value)) return false;
        this.value = value;
        this.observer.func(this.value);
        return true;
    }
    public void observe(Observer<T> observer) { this.observer = observer; }
    public Livedata(T value) { this.value = value; }
}
