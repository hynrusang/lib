package document;
import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import javax.swing.JComponent;

public abstract class HTMLElement<T extends HTMLElement<?>> {
	protected String identity;
	protected JComponent main;
	protected float[] percentInfo;
	protected int[] pxInfo;
	protected ArrayList<HTMLElement<?>> nodeList;
	
	/**
	 * Register a identifier for this element.
	 * @param identity Identifier to set for this element
	 * @return This element for the chaining method
	 */
	final public T identity(String identity) {
		this.identity = identity;
		return (T)this;
	}
	/**
	 * Sets the size of this element.
	 * @param widthp The horizontal area in percent that this element will occupy
	 * @param width The horizontal area in pixel that this element will occupy
	 * @param heightp The vertical area in percent that this element will occupy
	 * @param height The vertical area in pixel that this element will occupy
	 * @return This element for the chaining method
	 */
	final public T size(float widthp, int width, float heightp, int height) {
		percentInfo[0] = widthp;
		percentInfo[1] = heightp;
		pxInfo[0] = width;
		pxInfo[1] = height;
		return (T)this;
	}
	/**
	 * Sets the position of this element.
	 * @param xp The horizontal area in percent where this element will be located
	 * @param x The horizontal area in pixel where this element will be located
	 * @param yp The vertical area in percent where this element will be located
	 * @param y The Vertical area in pixel where this element will be located
	 * @return This element for the chaining method
	 */
	final public T position(float xp, int x, float yp, int y) {
		percentInfo[2] = xp;
		percentInfo[3] = yp;
		pxInfo[2] = x;
		pxInfo[3] = y;
		return (T)this;
	}
	/**
	 * Sets the background color of this element.
	 * @param color Color to set as background color
	 * @return This element for the chaining method
	 */
	final public T background(Color color) {
		main.setBackground(color);
		return (T)this;
	}
	/**
	 * Remove the element corresponding to the element from the child element.
	 * @param element Child elements to delete
	 */
	final public void removeChild(HTMLElement<?> element) {
		nodeList.remove(element);
		main.remove(element.main);
	}
	/**
	 * Append the element to child element.
	 * @param element Child elements to append
	 */
	final public void appendChild(HTMLElement<?> element) {
		nodeList.add(element);
		main.add(element.main);
	};
	
	protected HTMLElement(JComponent main, HTMLElement<?>... elements) {
		this.main = main;
		identity = "";
		percentInfo = new float[] {0, 0, 0, 0};
		pxInfo = new int[] {0, 0, 0, 0};
		nodeList = new ArrayList<HTMLElement<?>>();
		
		main.setLayout(null);
		main.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				HTMLElement.this.nodeList.forEach(element -> {
					int newWidth = (int)(HTMLElement.this.main.getWidth() * element.percentInfo[0] / 100 + element.pxInfo[0]);
					int newHeight = (int)(HTMLElement.this.main.getHeight() * element.percentInfo[1] / 100 + element.pxInfo[1]);
					int newX = (int)(HTMLElement.this.main.getWidth() * element.percentInfo[2] / 100 + element.pxInfo[2]);
					int newY = (int)(HTMLElement.this.main.getHeight() * element.percentInfo[3] / 100 + element.pxInfo[3]);
					element.main.setBounds(newX, newY, newWidth, newHeight);
				});
			}
		});
		for (HTMLElement<?> element: elements) appendChild(element);
	}
}
