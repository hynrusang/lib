package util.Document;
import javax.swing.JPanel;

public class Div extends HTMLElement {
	public Div(HTMLElement... elements) {
		super(new JPanel(), elements);
	}
}
