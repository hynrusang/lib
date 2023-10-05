package util.Document;
import java.awt.Component;
import java.awt.event.ComponentEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Html extends HTMLElement {
	private int windowx;
	private int windowy;
	private JFrame mainFrame;

	public void findTag(HTMLElement root) {
		for (HTMLElement element: root.nodeList) {
			System.out.println(element.getClass());
			if (!element.nodeList.isEmpty()) findTag(element);
		};
	}
	public void onCreate(Fragment bundle) {
		if (!mainFrame.getTitle().equals(bundle.title)) {
			mainFrame.setTitle(bundle.title);
			nodeList = bundle.nodeList;
			
			main.removeAll();
			nodeList.forEach(element -> main.add(element.main));
			main.dispatchEvent(new ComponentEvent(main, ComponentEvent.COMPONENT_RESIZED));
			main.repaint();
			findTag(this);
		}
	}
	public Html() {
		this("");
	}
	public Html(String headers) {
		super(new JPanel());
		windowx = 1040;
		windowy = 720;
		mainFrame = new JFrame();
		for (String header: headers.split(";")) {
			String[] parts = header.split(":");
			switch (parts[0].trim()) {
			case "window":
				windowx = Integer.parseInt(parts[1].split(",")[0].trim());
				windowy = Integer.parseInt(parts[1].split(",")[1].trim());
				break;
			}
		}
		mainFrame.add(main);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(windowx, windowy);
		mainFrame.setVisible(true);
	}
	
}