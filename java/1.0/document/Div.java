package document;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

final public class Div extends HTMLElement<Div> {
	public Div(HTMLElement<?>... elements) {
		super(new JPanel(), elements);
	}
}
