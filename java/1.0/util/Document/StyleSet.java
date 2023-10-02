import java.awt.Color;
import java.awt.LayoutManager;

public interface StyleSet<T> {
	public StyleSet size(int x, int y);
	public StyleSet layout(LayoutManager layout);
	public StyleSet background(Color color);
	public T end();
}