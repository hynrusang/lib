package util.Document;
import java.awt.Component;
import java.util.ArrayList;

public class Fragment {
	String title;
	ArrayList<Component> elements;
	public Fragment(Component... elements) {
		this("index", elements);
	}
	public Fragment(String title, Component... elements) {
		this.title = title;
		this.elements = new ArrayList<Component>();
		for (Component element: elements) this.elements.add(element);
	}
}
