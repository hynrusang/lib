package util.Document;
import java.awt.Color;
import java.awt.Component;

public interface HTMLStyle<T, F extends Component> {
	public T size(int widthp, int width, int heightp, int height);
	public T background(Color color);
	public F end();
}