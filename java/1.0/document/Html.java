package document;
import java.util.ArrayList;
import javax.swing.JFrame;

final public class Html {
	private static int[] window;
	private static JFrame mainFrame;
	private static Fragment bundle;
	
	public static HTMLElement<?> find(String query) {
		return find(bundle, query);
	}
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
	public static ArrayList<HTMLElement<?>> findAll(String query) {
		return findAll(bundle, query);
	}
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
	public static void swiping(Fragment bundle) {
		mainFrame.setTitle(bundle.title);
		if (Html.bundle != null) mainFrame.remove(Html.bundle.main);
		mainFrame.add(bundle.main);
		mainFrame.revalidate();
		mainFrame.repaint();
		Html.bundle = bundle;
		findAll("tag:Button").forEach(element -> {
			System.out.println(element.getClass());
		});;
		System.out.println();
		System.out.println(find("identity:true").getClass().getName());
	}
	
	private Html() { };
	public static void init() {
		init("");
	}
	public static void init(String headers) {
		window = new int[] { 1040, 720 };
		mainFrame = new JFrame();
		
		for (String header: headers.split(";")) {
			String[] parts = header.split(":");
			switch (parts[0].trim()) {
			case "window":
				window[0] = Integer.parseInt(parts[1].split(",")[0].trim());
				window[1] = Integer.parseInt(parts[1].split(",")[1].trim());
				break;
			}
		}
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(window[0], window[1]);
		mainFrame.setVisible(true);
	}
	
}
