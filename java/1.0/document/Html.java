package document;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

final public class Html {
	private static int[] window;
	private static JFrame mainFrame;
	private static JComponent main;

	public static void find(HTMLElement<?> node) {
		for (HTMLElement<?> element: node.nodeList) {
			System.out.println(element.getClass().getName());
			if (!element.nodeList.isEmpty()) find(element);
		};
	}
	public static void swiping(Fragment bundle) {
		if (main == null || !main.equals(bundle.main)) {
			mainFrame.setTitle(bundle.title);
			mainFrame.remove(main);
			mainFrame.add(bundle.main);
			mainFrame.revalidate();
			mainFrame.repaint();
			main = bundle.main;
		}
	}
	
	public static void init() {
		init("");
	}
	public static void init(String headers) {
		window = new int[] { 1040, 720 };
		mainFrame = new JFrame();
		main = new JPanel();
		
		for (String header: headers.split(";")) {
			String[] parts = header.split(":");
			switch (parts[0].trim()) {
			case "window":
				window[0] = Integer.parseInt(parts[1].split(",")[0].trim());
				window[1] = Integer.parseInt(parts[1].split(",")[1].trim());
				break;
			}
		}
		mainFrame.add(main);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(window[0], window[1]);
		mainFrame.setVisible(true);
	}
	
}
