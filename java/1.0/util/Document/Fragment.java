package util.Document;
import java.awt.Color;
import java.awt.Component;
import java.awt.LayoutManager;
import javax.swing.JPanel;

public class Fragment extends JPanel {
	private static final long serialVersionUID = 1L;
	public class Style implements StyleSet {

		@Override
		public Style size(int x, int y) {
			Fragment.this.setSize(x, y);
			return this;
		}

		@Override
		public Style layout(LayoutManager layout) {
			Fragment.this.setLayout(layout);
			return this;
		}

		@Override
		public Style background(Color color) {
			Fragment.this.setBackground(color);
			return this;
		}
		
		public Fragment end() {
			return Fragment.this;
		}
	}
	public Style withStyle = new Style();
	public Fragment() { };
	public Fragment(Component element) {
		add(element);
	}
	public Fragment(Component element, String placement) {
		add(element, placement);
	}
	public Fragment(Component[] elements) {
		for (Component element: elements) add(element);
	}
	public Fragment(Component[] elements, String[] placements) {
		for (int i = 0; i < elements.length; i++) add(elements[i], placements[i]);
	}
}
