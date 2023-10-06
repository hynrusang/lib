package document;
import java.util.ArrayList;
import javax.swing.JFrame;

final public class Html {
	private static int[] window;
	private static JFrame mainFrame;
	private static Fragment bundle;
	
	public static ArrayList<HTMLElement<?>> find(HTMLElement<?> target) {
		return find(bundle, target);
	}
	public static ArrayList<HTMLElement<?>> find(HTMLElement<?> node, HTMLElement<?> target) {
		ArrayList<HTMLElement<?>> temp = new ArrayList<HTMLElement<?>>();
		for (HTMLElement<?> element: node.nodeList) {
			if (element.getClass().equals(target.getClass())) temp.add(element);
			if (!element.nodeList.isEmpty()) temp.addAll(find(element, target));
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
		find(new Button()).forEach(element -> {
			System.out.println(element.getClass().getName());
		});;
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
