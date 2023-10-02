package util.Document;
import java.awt.Color;
import java.awt.Component;
import java.awt.LayoutManager;

public interface HTMLCustomize<T, F extends Component> {
	public T size(String width, String height);
	public T size(int widthp, int width, int heightp, int height);
	public T position(String x, String y);
	public T position(int xp, int x, int yp, int y);
	public T layout(LayoutManager layout);
	public T background(Color color);
	public F end();
}