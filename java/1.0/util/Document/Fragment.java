package util.Document;
import java.awt.Color;
import java.awt.Component;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class Fragment extends JPanel {
	private String name;
	public class Style implements StyleSet<Fragment, Style> {

		@Override
		public StyleSet size(int x, int y) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StyleSet layout(LayoutManager layout) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StyleSet background(Color color) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Fragment end() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	public String getName() {
		return name;
	}
	public Fragment(String name, Component... components) {
		this.name = name;
		for (Component component: components) add(component);
	}
}
