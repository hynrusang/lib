package util.Document;
import java.awt.Color;
import java.awt.LayoutManager;
import javax.swing.JFrame;

public class Body extends JFrame {
	private static final long serialVersionUID = 1L;
	public class Style implements StyleSet {
		@Override
		public StyleSet setSize(int x, int y) {
			Body.this.setSize(x, y);
			return this;
		}

		@Override
		public StyleSet setLayout(LayoutManager layout) {
			Body.this.setLayout(layout);
			return this;
		}

		@Override
		public StyleSet setBackground(Color color) {
			Body.this.setBackground(color);
			return this;
		}

		public StyleSet setTitle(String title) {
			Body.this.setTitle(title);
			return this;
		}
	}
	public Style style;
	public Body() {
		setTitle("Document");
		setSize(720, 440);
		setVisible(true);
		this.style = new Style();
	}
}
