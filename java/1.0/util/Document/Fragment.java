package util.Document;
import java.util.ArrayList;

public class Fragment {
	String title;
	ArrayList<HTMLElement<?>> nodeList;
	public Fragment(HTMLElement<?>... elements) {
		this("index", elements);
	}
	public Fragment(String title, HTMLElement<?>... elements) {
		this.title = title;
		nodeList = new ArrayList<HTMLElement<?>>();
		for (HTMLElement<?> element: elements) nodeList.add(element);
	}
}
