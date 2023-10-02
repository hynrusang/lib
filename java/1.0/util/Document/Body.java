package util.Document;
import java.awt.Color;
import java.awt.Component;
import java.awt.LayoutManager;
import javax.swing.JPanel;

public class Body extends JPanel {
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
	}
	public Style withStyle = new Style();
	public Body(Component... elements) {
		for (Component element: elements) add(element);
	}
}
