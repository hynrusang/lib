package util.Document;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JPanel;

public class Div extends JPanel {
	private static final long serialVersionUID = 1L;
	public class Customize implements HTMLCustomize<Customize, Div> {
		@Override
		public Customize size(String width, String height) {
			int widthRange = Integer.parseInt(width.replaceAll("[^0-9]", ""));
			int heightRange = Integer.parseInt(height.replaceAll("[^0-9]", ""));
			Div.this.addComponentListener(new ComponentAdapter() {
				public void componentResized(ComponentEvent e) {
					Component parent = e.getComponent().getParent();
					int newWidth = (width.indexOf("%") != -1) ? parent.getWidth() * widthRange / 100 : widthRange;
					int newHeight = (height.indexOf("%") != -1) ? parent.getHeight() * heightRange / 100 : heightRange;
					Div.this.setSize(newWidth, newHeight);
				}
			});
			return this;
		}
		
		@Override
		public Customize size(int widthp, int width, int heightp, int height) {
			Div.this.addComponentListener(new ComponentAdapter() {
				public void componentResized(ComponentEvent e) {
					Component parent = e.getComponent().getParent();
					int newWidth = parent.getWidth() * widthp / 100 + width;
					int newHeight = parent.getHeight() * heightp / 100 + height;
					Div.this.setSize(newWidth, newHeight);
				}
			});
			return this;
		}
		
		@Override
		public Customize position(String x, String y) {
			int xRange = Integer.parseInt(x.replaceAll("[^0-9]", ""));
			int yRange = Integer.parseInt(y.replaceAll("[^0-9]", ""));
			Div.this.addComponentListener(new ComponentAdapter() {
				public void componentResized(ComponentEvent e) {
					Component parent = e.getComponent().getParent();
					int newX = (x.indexOf("%") != -1) ? parent.getWidth() * xRange / 100 :xRange;
					int newY = (y.indexOf("%") != -1) ? parent.getHeight() * yRange / 100 : yRange;
					Div.this.setLocation(newX, newY);
				}
			});
			return this;
		}
		
		@Override
		public Customize position(int xp, int x, int yp, int y) {
			Div.this.addComponentListener(new ComponentAdapter() {
				public void componentResized(ComponentEvent e) {
					Component parent = e.getComponent().getParent();
					int newX = parent.getWidth() * xp / 100 + x;
					int newY = parent.getHeight() * yp / 100 + y;
					Div.this.setLocation(newX, newY);
				}
			});
			return this;
		}

		@Override
		public Customize background(Color color) {
			Div.this.setBackground(color);
			return this;
		}
		
		@Override
		public Div end() {
			return Div.this;
		}
	}
	public Customize customize = new Customize();
	public Div(Component... components) {
		for (Component component: components) add(component);
	}
}