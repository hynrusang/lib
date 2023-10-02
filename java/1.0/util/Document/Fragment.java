package util.Document;
import java.awt.Color;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Fragment extends JPanel {
	private ArrayList<Component> children = new ArrayList<Component>();
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
	public void appendChild(Component component) {
		add(component);
		children.add(component);
	}
	public void appendChild(Component component, Object constraints) {
		add(component, constraints);
		children.add(component);
	}
	public void removeChild(Component component) {
		remove(component);
		children.remove(component);
	}
	public Customize customize = new Customize();
	public String getName() {
		return name;
	}
	public Fragment(String name, Component... components) {
		this.name = name;
		addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				children.forEach(child -> child.dispatchEvent(new ComponentEvent(child, ComponentEvent.COMPONENT_RESIZED)));
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				children.forEach(child -> child.dispatchEvent(new ComponentEvent(child, ComponentEvent.COMPONENT_MOVED)));
			}

			@Override
			public void componentShown(ComponentEvent e) {
				children.forEach(child -> child.dispatchEvent(new ComponentEvent(child, ComponentEvent.COMPONENT_SHOWN)));
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				children.forEach(child -> child.dispatchEvent(new ComponentEvent(child, ComponentEvent.COMPONENT_HIDDEN)));
			}
		});
		for (Component component: components) appendChild(component);
	}
}
