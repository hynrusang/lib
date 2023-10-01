package util.Document;
import java.awt.Color;
import java.awt.LayoutManager;

public interface StyleSet {
	StyleSet setSize(int x, int y);
	StyleSet setLayout(LayoutManager layout);
	StyleSet setBackground(Color color);
}