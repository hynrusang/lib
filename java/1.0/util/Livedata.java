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
         * get <b>value</b> of livedata.<br>
         * @return value of livedata
         */
	public T getValue() { return this.value; }
	/**
         * set <b>value</b> of livedata.<br>
	 * if data was changed, call <b>observer</b> function.<br>
         * (Check with the <b>equals</b> method of the object)
	 * @param value new value to set
         * @return whether data was changed. 
	 * .</pre>
         */
	public boolean setValue(T value) {
		if (this.value.equals(value)) return false;
		this.value = value;
		this.observer.launch(this.value);
		return true;
	}
	/**
         * Registers an <b>observer</b> function to call when the value of livedata changes.<br>
	 * The observer function is written in the form of a <b>Lambda</b> function.
         * @param observer Lambda function to run automatically when the value change.
	 */
	public void observe(Observer<T> observer) { 
		this.observer = observer; 
	}
	public Livedata(T value) {
		this.value = value; 
	}
}
