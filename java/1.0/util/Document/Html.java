package util.Document;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Html extends JFrame {
	private static final long serialVersionUID = 1L;
	public JPanel head;
	public JPanel body;
	public class Style implements StyleSet {
		@Override
		public Style size(int x, int y) {
			Html.this.setSize(x, y);
			return this;
		}

		@Override
		public Style layout(LayoutManager layout) {
			Html.this.setLayout(layout);
			return this;
		}

		@Override
		public Style background(Color color) {
			Html.this.setBackground(color);
			return this;
		}
		
		public Html end() {
			return Html.this;
		}
	}
	public Style withStyle = new Style();
	public Html() {
		this(new JPanel(), new Body());
	}
	
	public Html(JPanel head, JPanel body) {
		this.head = head;
		this.body = body;
		
		add(this.head, BorderLayout.NORTH);
		add(this.body, BorderLayout.CENTER);
		
		setTitle("Document");
		setSize(720, 440);
		setVisible(true);
	}
}
