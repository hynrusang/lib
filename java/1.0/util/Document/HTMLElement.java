package util.Document;
import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import javax.swing.JComponent;

public abstract class HTMLElement<T extends HTMLElement<?>> {
	protected static final long serialVersionUID = 1L;
	protected JComponent main;
	protected int[] percentInfo;
	protected int[] pxInfo;
	protected ArrayList<HTMLElement<?>> nodeList;
	
	protected void setMain(JComponent main) {
		this.main = main;
		main.setLayout(null);
		main.addComponentListener(new ComponentAdapter() {
			@Override
            public void componentResized(ComponentEvent e) {
				HTMLElement.this.nodeList.forEach(element -> {
                	int[] percentInfo = element.percentInfo;
            		int[] pxInfo = element.pxInfo;
            		int newWidth = HTMLElement.this.main.getWidth() * percentInfo[0] / 100 + pxInfo[0];
            		int newHeight = HTMLElement.this.main.getHeight() * percentInfo[1] / 100 + pxInfo[1];
            		int newX = HTMLElement.this.main.getWidth() * percentInfo[2] / 100 + pxInfo[2];
            		int newY = HTMLElement.this.main.getHeight() * percentInfo[3] / 100 + pxInfo[3];
            		element.main.setBounds(newX, newY, newWidth, newHeight);
                });
            }
        });
	}
	protected void appendChild(HTMLElement<?> element) {
		nodeList.add(element);
		main.add(element.main);
	};
	protected HTMLElement(JComponent main, HTMLElement<?>... elements) {
		setMain(main);
		percentInfo = new int[] {0, 0, 0, 0};
		pxInfo = new int[] {0, 0, 0, 0};
		nodeList = new ArrayList<HTMLElement<?>>();
		style = new Style();
		
		for (HTMLElement<?> element: elements) appendChild(element);
	}
	
	public class Style {
		public Style size(int widthp, int width, int heightp, int height) {
			percentInfo[0] = widthp;
			percentInfo[1] = heightp;
			pxInfo[0] = width;
			pxInfo[1] = height;
			return this;
		}

		public Style position(int xp, int x, int yp, int y) {
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

		public T end() {
			return (T)T.this;
		}
	}
	public Style style;
}
