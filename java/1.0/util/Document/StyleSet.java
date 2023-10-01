package util.Document;
import java.awt.Color;
import java.awt.LayoutManager;

public interface StyleSet {
	StyleSet size(int x, int y);
	StyleSet layout(LayoutManager layout);
	StyleSet background(Color color);
}