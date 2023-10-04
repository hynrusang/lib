package util.Document;
import java.awt.Color;

import javax.swing.JComponent;

public interface HTMLStyle<T, F extends HTMLElement<?>> {
	public T size(int widthp, int width, int heightp, int height);
	public T position(int xp, int x, int yp, int y);
	public T background(Color color);
	public F end();
}