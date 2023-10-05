package util.Document;
import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import javax.swing.JComponent;

public abstract class HTMLElement {
	protected String name;
	protected JComponent main;
	protected float[] percentInfo;
	protected int[] pxInfo;
	protected ArrayList<HTMLElement> nodeList;
	public Style style;
	
	public class Style {
		public Style size(float widthp, int width, float heightp, int height) {
			percentInfo[0] = widthp;
			percentInfo[1] = heightp;
			pxInfo[0] = width;
			pxInfo[1] = height;
			return this;
		}

		public Style position(float xp, int x, float yp, int y) {
			percentInfo[2] = xp;
			percentInfo[3] = yp;
			pxInfo[2] = x;
			pxInfo[3] = y;
			return this;
		}
		
		public Style background(Color color) {
			main.setBackground(color);
			return this;
		}

		public HTMLElement end() {
			return HTMLElement.this;
		}
	}
	
	protected void appendChild(HTMLElement element) {
		nodeList.add(element);
		main.add(element.main);
	};
	protected HTMLElement(JComponent main, HTMLElement... elements) {
		this.main = main;
		percentInfo = new float[] {0, 0, 0, 0};
		pxInfo = new int[] {0, 0, 0, 0};
		nodeList = new ArrayList<HTMLElement>();
		style = new Style();
		
		main.setLayout(null);
		main.addComponentListener(new ComponentAdapter() {
			@Override
            public void componentResized(ComponentEvent e) {
				HTMLElement.this.nodeList.forEach(element -> {
					float[] percentInfo = element.percentInfo;
            		int[] pxInfo = element.pxInfo;
            		int newWidth = (int)(HTMLElement.this.main.getWidth() * percentInfo[0] / 100 + pxInfo[0]);
            		int newHeight = (int)(HTMLElement.this.main.getHeight() * percentInfo[1] / 100 + pxInfo[1]);
            		int newX = (int)(HTMLElement.this.main.getWidth() * percentInfo[2] / 100 + pxInfo[2]);
            		int newY = (int)(HTMLElement.this.main.getHeight() * percentInfo[3] / 100 + pxInfo[3]);
            		element.main.setBounds(newX, newY, newWidth, newHeight);
                });
            }
        });
		for (HTMLElement element: elements) appendChild(element);
	}
}
