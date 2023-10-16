package document;
import javax.swing.JButton;
import java.awt.event.ActionListener;

final public class Button extends HTMLElement<Button> {
	/**
	 * Registers the listener that runs when you click a button.
	 * @param listener Listener to run when a button is clicked
	 * @return This element for the chaining method
	 */
	public Button onClick(ActionListener listener) {
		((JButton)main).addActionListener(listener);
		return this;
	}
	
	public Button() {
		this("");
	}
	public Button(String text) {
		super(new JButton());
		this.text = text;
		((JButton)main).setText(text);
	}
}
