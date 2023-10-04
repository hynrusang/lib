package util.Document;

import javax.swing.JPanel;

public class Div extends HTMLElement<Div> {
	public Div(HTMLElement<?>... elements) {
		super(new JPanel(), elements);
	}
}
