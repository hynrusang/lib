package util.Document;
import java.awt.Color;
import java.awt.LayoutManager;
import javax.swing.JPanel;

public class Fragment extends JPanel {
	private static final long serialVersionUID = 1L;
	public class Style {
		public Style setSize(int x, int y) { 
			Fragment.this.setSize(x, y); 
			return this;
		}
		public Style setLayout(LayoutManager layout) {
			Fragment.this.setLayout(layout);
			return this;
		}
		public Style setBackground(Color color) {
			Fragment.this.setBackground(color);
			return this;
		}
	}
	public Style style;
	public Fragment() {
		setSize(720, 440);
		setVisible(true);
		this.style = new Style();
	}
}
