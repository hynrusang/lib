package util;

/**
 * An class that has an initial value and automatically executes a observer function when the value changes.
 * @param <T> instanceof Object
 */
public class Livedata<T> {
	private T value;
	private Observer<T> observer;
	
	public static interface Observer<T> {
		void launch(T it);
	}
	
	/**
	 * Get value of livedata.<br>
	 * @return Value of livedata
	 */
	public T getValue() { return this.value; }
	/**
	 * set value of livedata.<br>
	 * if data was changed, call observer function.
	 * @param value new value to set
	 * @return whether data was changed. 
	 */
	public boolean setValue(T value) {
		if (this.value.equals(value)) return false;
		this.value = value;
		this.observer.launch(this.value);
		return true;
	}
	/**
	 * Registers an observer to call when the value of livedata changes.<br>
	 * @param observer Observer to run automatically when the value change.
	 */
	public void observe(Observer<T> observer) { 
		this.observer = observer; 
	}
	public Livedata(T value) {
		this.value = value; 
	}
}
