package util.Document;
import java.awt.Color;
import java.awt.Component;

public interface HTMLCustomize<T, F extends Component> {
	public T size(String width, String height);
	public T size(int widthp, int width, int heightp, int height);
	public T position(String x, String y);
	public T position(int xp, int x, int yp, int y);
	public T background(Color color);
	public F end();
}