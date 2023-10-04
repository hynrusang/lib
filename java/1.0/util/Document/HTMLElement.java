package util.Document;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JComponent;

public abstract class HTMLElement<T extends HTMLElement<?>> {
	protected static final long serialVersionUID = 1L;
	protected JComponent main;
	protected int[] percentInfo;
	protected int[] pxInfo;
	protected ArrayList<HTMLElement<?>> nodeList;
	
	protected void appendChild(HTMLElement<?> element) {
		nodeList.add(element);
		main.add(element.main);
	};
	protected HTMLElement(HTMLElement<?>... elements) {
		percentInfo = new int[] {0, 0, 0, 0};
		pxInfo = new int[] {0, 0, 0, 0};
		nodeList = new ArrayList<HTMLElement<?>>();
		style = new Style();
	}
	
	public class Style implements HTMLStyle<Style, T> {
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
			main.setBackground(color);
			return this;
		}

		@Override
		public T end() {
			return (T)T.this;
		}
	}
	public Style style;
}
