package document;
import java.util.ArrayList;
import javax.swing.JFrame;

final public class Html {
	private static int[] window;
	private static Fragment[] navigation;
	private static JFrame mainFrame;
	private static int currentNum;
	
	/**
	 * Return the first of the elements corresponding to the query.
	 * @param query String query that begin with tag: or identity:
	 * @return First of the elements corresponding to the query
	 */
	public static HTMLElement<?> find(String query) {
		return find(navigation[currentNum], query);
	}
	/**
	 * Return the first of the elements corresponding to the query.
	 * @param node Parent element to start looking for
	 * @param query String query that begin with tag: or identity:
	 * @return First element matching String query
	 */
	public static HTMLElement<?> find(HTMLElement<?> node, String query) {
		String[] part = query.split(":");
		for (HTMLElement<?> element: node.nodeList) {
			switch (part[0]) {
			case "tag":
				if (element.getClass().getSimpleName().equals(part[1])) return element;
				break;
			case "identity":
				if (element.identity.equals(part[1])) return element;
				break;
			}
			if (!element.nodeList.isEmpty()) {
	            HTMLElement<?> found = find(element, query);
	            if (found != null) return found;
	        }
		};
		return null;
	}
	/**
	 * Returns all elements corresponding to the query string.
	 * @param query String query that begin with tag: or identity:
	 * @return All elements corresponding to the query string.
	 */
	public static ArrayList<HTMLElement<?>> findAll(String query) {
		return findAll(navigation[currentNum], query);
	}
	/**
	 * Returns all elements corresponding to the query string.
	 * @param node Parent element to start looking for
	 * @param query String query that begin with tag: or identity:
	 * @return All elements corresponding to the query string.
	 */
	public static ArrayList<HTMLElement<?>> findAll(HTMLElement<?> node, String query) {
		String[] part = query.split(":");
		ArrayList<HTMLElement<?>> temp = new ArrayList<HTMLElement<?>>();
		for (HTMLElement<?> element: node.nodeList) {
			switch (part[0]) {
			case "identity":
				if (element.identity.equals(part[1])) temp.add(element);
				break;
			case "tag":
				if (element.getClass().getSimpleName().equals(part[1])) temp.add(element);
				break;
			}
			if (!element.nodeList.isEmpty()) temp.addAll(findAll(element, query));
		};
		return temp;
	}
	/**
	 * Replace the currently displayed screen to a bundle.
	 * @param bundle New fragment to be shown
	 */
	public static void navigate(int num) {
		if (Html.currentNum != num) {
			mainFrame.setTitle(navigation[num].title);
			mainFrame.remove(navigation[currentNum].main);
			mainFrame.add(navigation[num].main);
			mainFrame.revalidate();
			mainFrame.repaint();
			Html.currentNum = num;
		}
	}
	
	private Html() { };
	/**
	 * Initialize the Frame window.
	 */
	public static void init(Fragment... navigation) {
		init("", navigation);
	}
	/**
	 * Initialize the Frame window.
	 * @param headers String query to set in frame window
	 */
	public static void init(String headers, Fragment... navigation) {
		if (window != null) throw new RuntimeException("html is already init.");
		else {
			window = new int[] { 1040, 720 };
			Html.navigation = navigation;
			mainFrame = new JFrame();
			currentNum = 0;
			
			mainFrame.setTitle(navigation[0].title);
			for (String header: headers.split(";")) {
				String[] parts = header.split(":");
				switch (parts[0].trim()) {
				case "window":
					window[0] = Integer.parseInt(parts[1].split(",")[0].trim());
					window[1] = Integer.parseInt(parts[1].split(",")[1].trim());
					break;
				}
			}
			mainFrame.add(navigation[0].main);
			mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			mainFrame.setSize(window[0], window[1]);
			mainFrame.setVisible(true);
		}
	}
}
