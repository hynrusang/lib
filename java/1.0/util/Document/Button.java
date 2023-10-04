package util.Document;
import javax.swing.JButton;

public class Button extends HTMLElement<Button> {
	public Button() {
		super(new JButton());
	}
	public Button(String test) {
		this();
		((JButton)main).setText(test);
	}
}
