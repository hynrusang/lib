package util.Document;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Div extends JPanel implements HTMLElement {
	private static final long serialVersionUID = 1L;
	private int[] percentInfo;
	private int[] pxInfo;
	private ArrayList<Component> elements;
	
	public class Style implements HTMLStyle<Style, Div> {
		@Override
		public Style size(int widthp, int width, int heightp, int height) {
			percentInfo[0] = widthp;
			percentInfo[1] = heightp;
			pxInfo[0] = width;
			pxInfo[1] = height;
			return this;
		}

		@Override
		public Style position(int xp, int x, int yp, int y) {
			percentInfo[2] = xp;
			percentInfo[3] = yp;
			pxInfo[2] = x;
			pxInfo[3] = y;
			return this;
		}
		
		@Override
		public Style background(Color color) {
			Div.this.setBackground(color);
			return this;
		}

		@Override
		public Div end() {
			return Div.this;
		}
	}
	@Override
	public int[] getPercentInfo() {
		return percentInfo.clone();
	}
	@Override
	public int[] getPxInfo() {
		return pxInfo.clone();
	}
	public Style style;
	public Div(Component... elements) {
		percentInfo = new int[] {0, 0, 0, 0};
		pxInfo = new int[] {0, 0, 0, 0};
		this.elements = new ArrayList<Component>();;
		for (Component element: elements) {
			this.elements.add(element);
			add(element);
		}
		addComponentListener(new ComponentAdapter() {
			@SuppressWarnings("deprecation")
			@Override
            public void componentResized(ComponentEvent e) {
                Div.this.elements.forEach(element -> {
                	if (element instanceof HTMLElement) {
                		int[] percentInfo = ((HTMLElement)element).getPercentInfo();
                		int[] pxInfo = ((HTMLElement)element).getPxInfo();
                		int newWidth = Div.this.getWidth() * percentInfo[0] / 100 + pxInfo[0];
                		int newHeight = Div.this.getHeight() * percentInfo[1] / 100 + pxInfo[1];
                		int newX = Div.this.getWidth() * percentInfo[2] / 100 + pxInfo[2];
                		int newY = Div.this.getHeight() * percentInfo[3] / 100 + pxInfo[3];
                		element.setBounds(newX, newY, newWidth, newHeight);
                	}
                });
            }
        });
		style = new Style();
		setLayout(null);
	}
}
