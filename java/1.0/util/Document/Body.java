package util.Document;
import java.awt.Component;
import javax.swing.JFrame;

public class Body extends JFrame {
	public class Style {
		public Style setTitle(String title) {
			Body.this.setTitle(title);
			return this;
		}
		public Style setSize(int x, int y) { 
			Body.this.setSize(x, y); 
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
