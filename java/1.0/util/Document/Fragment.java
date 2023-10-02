package util.Document;
import java.awt.Color;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;

public class Fragment extends JPanel {
	private String name;
	
	public class Customize implements HTMLCustomize<Customize, Fragment> {
		@Override
		public Customize size(String width, String height) {
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
		public Customize position(String x, String y) {
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
		public Customize layout(LayoutManager layout) {
			Fragment.this.setLayout(layout);
			return this;
		}

		@Override
		public Customize background(Color color) {
			Fragment.this.setBackground(color);
			return this;
		}
		
		@Override
		public Fragment end() {
			return Fragment.this;
		}
	}
	public Customize customize = new Customize();
	public String getName() {
		return name;
	}
	public Fragment(String name, Component... components) {
		this.name = name;
		for (Component component: components) add(component);
	}
}
