package util.Document;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;

public class Fragment extends JPanel {
	private static final long serialVersionUID = 1L;
	private String id;
	
	public class Style implements HTMLStyle<Style, Fragment> {
		@Override
		public Style size(String width, String height) {
			int widthRange = Integer.parseInt(width.replaceAll("[^0-9]", ""));
			int heightRange = Integer.parseInt(height.replaceAll("[^0-9]", ""));
			Fragment.this.addComponentListener(new ComponentAdapter() {
				public void componentResized(ComponentEvent e) {
					Component parent = e.getComponent().getParent();
					int newWidth = (width.indexOf("%") != -1) ? parent.getWidth() * widthRange / 100 : widthRange;
					int newHeight = (height.indexOf("%") != -1) ? parent.getHeight() * heightRange / 100 : heightRange;
					Fragment.this.setSize(newWidth, newHeight);
				}
			});
			return this;
		}
		
		@Override
		public Style size(int widthp, int width, int heightp, int height) {
			Fragment.this.addComponentListener(new ComponentAdapter() {
				public void componentResized(ComponentEvent e) {
					Component parent = e.getComponent().getParent();
					int newWidth = parent.getWidth() * widthp / 100 + width;
					int newHeight = parent.getHeight() * heightp / 100 + height;
					Fragment.this.setSize(newWidth, newHeight);
				}
			});
			return this;
		}
		
		@Override
		public Style position(String x, String y) {
			int xRange = Integer.parseInt(x.replaceAll("[^0-9]", ""));
			int yRange = Integer.parseInt(y.replaceAll("[^0-9]", ""));
			Fragment.this.addComponentListener(new ComponentAdapter() {
				public void componentResized(ComponentEvent e) {
					Component parent = e.getComponent().getParent();
					int newX = (x.indexOf("%") != -1) ? parent.getWidth() * xRange / 100 :xRange;
					int newY = (y.indexOf("%") != -1) ? parent.getHeight() * yRange / 100 : yRange;
					Fragment.this.setLocation(newX, newY);
				}
			});
			return this;
		}
		
		@Override
		public Style position(int xp, int x, int yp, int y) {
			Fragment.this.addComponentListener(new ComponentAdapter() {
				public void componentResized(ComponentEvent e) {
					Component parent = e.getComponent().getParent();
					int newX = parent.getWidth() * xp / 100 + x;
					int newY = parent.getHeight() * yp / 100 + y;
					Fragment.this.setLocation(newX, newY);
				}
			});
			return this;
		}

		@Override
		public Style background(Color color) {
			Fragment.this.setBackground(color);
			return this;
		}
		
		@Override
		public Fragment end() {
			return Fragment.this;
		}
	}
	/**
	 * Open a method chain that allows you to edit the style of this component.
	 */
	public Style style = new Style();
	String getId() {
		return id;
	}
	/**
	 * Create a unique semantic tag that makes up different pages within the Html tag.
	 * @param id Unique name value for Fragment
	 * @param components Elements to Set as Subcomponents
	 */
	public Fragment(String id, Component... components) {
		this.id = id;
		for (Component component: components) add(component);
	}
}
