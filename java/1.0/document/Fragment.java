package document;
import javax.swing.JPanel;

final public class Fragment extends HTMLElement<Fragment> {
	String title;
	
	public Fragment(HTMLElement<?>... elements) {
		this("index", elements);
	}
	public Fragment(String title, HTMLElement<?>... elements) {
		super(new JPanel(), elements);
		this.title = title;
	}
}
