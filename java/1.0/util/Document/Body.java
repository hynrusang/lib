package util.Document;
import java.awt.Color;
import java.awt.Component;
import java.awt.LayoutManager;
import javax.swing.JFrame;

public class Body extends JFrame {
	private static final long serialVersionUID = 1L;
	public class Style implements StyleSet {
		@Override
		public Style size(int x, int y) {
			Body.this.setSize(x, y);
			return this;
		}

		@Override
		public Style layout(LayoutManager layout) {
			Body.this.setLayout(layout);
			return this;
		}

		@Override
		public Style background(Color color) {
			Body.this.setBackground(color);
			return this;
		}
		
		public Body end() {
			return Body.this;
		}

		public Style title(String title) {
			Body.this.setTitle(title);
			return this;
		}
	}
	public Style withStyle = new Style();
	public Body() {
		setTitle("Document");
		setSize(720, 440);
		setVisible(true);
	}
	public Body(Component element) {
		this();
		add(element);
	}
	public Body(Component element, String placement) {
		this();
		add(element, placement);
	}
	public Body(Component[] elements) {
		this();
		for (Component element: elements) add(element);
	}
	public Body(Component[] elements, String[] placements) {
		this();
		for (int i = 0; i < elements.length; i++) add(elements[i], placements[i]);
	}
}
