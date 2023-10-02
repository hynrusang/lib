package util.Document;
import java.awt.Color;
import java.awt.Component;
import java.awt.LayoutManager;

public interface HTMLCustomize<T, F extends Component> {
	public T size(int x, int y);
	public T layout(LayoutManager layout);
	public T background(Color color);
	public F end();
}