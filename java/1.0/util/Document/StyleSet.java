package util.Document;
import java.awt.Color;
import java.awt.Component;
import java.awt.LayoutManager;

public interface StyleSet<F, T extends Component> {
	public F size(int x, int y);
	public F layout(LayoutManager layout);
	public F background(Color color);
	public T end();
}