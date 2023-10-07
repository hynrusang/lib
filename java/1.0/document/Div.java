package document;
import javax.swing.JPanel;

final public class Div extends HTMLElement<Div> {
	public Div(HTMLElement<?>... elements) {
		super(new JPanel(), elements);
	}
}
