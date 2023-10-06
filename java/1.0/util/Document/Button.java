package util.Document;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class Button extends HTMLElement<Button> {
	public Button onClick(ActionListener listener) {
		((JButton)main).addActionListener(listener);
		return this;
	}
	public Button() {
		this("");
	}
	public Button(String text) {
		super(new JButton());
		((JButton)main).setText(text);
	}
}
