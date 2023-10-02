package util.Document;
import java.awt.Color;
import java.awt.Component;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class Fragment extends JPanel {
	private String name;
	public String getName() {
		return name;
	}
	public class Style implements HTMLStyle<Style, Fragment> {
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

		@Override
		public Fragment end() {
			return Fragment.this;
		}
	}
	public Style withStyle = new Style();
	public Fragment(String name, Component... components) {
		this.name = name;
		for (Component component: components) add(component);
	}
}
