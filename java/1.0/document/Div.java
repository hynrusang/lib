package document;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;

class DivInner extends HTMLElement<DivInner> {
	public DivInner(HTMLElement<?>... elements) {
		super(new JPanel(), elements);
	}
}
final public class Div extends HTMLElement<Div> {
	private DivInner inner;
	public Div(HTMLElement<?>... elements) {
		super(new JScrollPane());
		inner = new DivInner(elements);
		border(null, 0);
		((JScrollPane)main).setViewportView(inner.main);
		((JScrollPane)main).setLayout(new ScrollPaneLayout());
	}
}
