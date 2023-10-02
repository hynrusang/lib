package util.Document;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Head extends JPanel {
	private static final long serialVersionUID = 1L;
	public class Style implements StyleSet {

		@Override
		public Style size(int x, int y) {
			Head.this.setSize(x, y);
			return this;
		}

		@Override
		public Style layout(LayoutManager layout) {
			Head.this.setLayout(layout);
			return this;
		}

		@Override
		public Style background(Color color) {
			Head.this.setBackground(color);
			return this;
		}
		
		public Head end() {
			return Head.this;
		}
		
	}
	public Style withStyle = new Style();
	public Head() throws Exception {
		this("index");
	}
	public Head(String name, String... metas) throws Exception {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel label = new JLabel("document: " + name);
		for (String meta: metas) {
			if (meta.indexOf("url-font") != -1) {
				String[] syntax = meta.split(":")[1].split(",");
				if (syntax.length < 2) throw new RuntimeException("Syntax: url-font requires font, size parameters.");
				label.setFont(new Font(syntax[0].trim(), Font.PLAIN, Integer.parseInt(syntax[1].trim())));
			}
		}
		add(label);
	}
}
