package util.Document;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Doc {
	private static int[] window;
	private static JFrame mainFrame;
	private static JPanel main;
	private static ArrayList<HTMLElement<?>> nodeList;

	public static void find(HTMLElement<?> node) {
		for (HTMLElement<?> element: node.nodeList) {
			System.out.println(element.getClass().getName());
			if (!element.nodeList.isEmpty()) find(element);
		};
	}
	public static void switching(Fragment bundle) {
		if (!mainFrame.getTitle().equals(bundle.title)) {
			mainFrame.setTitle(bundle.title);
			nodeList.removeAll(nodeList);
			main.removeAll();
			bundle.nodeList.forEach(element -> {
				nodeList.add(element);
				main.add(element.main);
			});
			main.dispatchEvent(new ComponentEvent(main, ComponentEvent.COMPONENT_RESIZED));
			main.repaint();
		}
	}
	public static void init() {
		this("");
	}
	public static void init(String headers) {
		window = new int[] { 1040, 720 };
		mainFrame = new JFrame();
		main = new JPanel();
		nodeList = new ArrayList<HTMLElement<?>>();
		
		for (String header: headers.split(";")) {
			String[] parts = header.split(":");
			switch (parts[0].trim()) {
			case "window":
				window[0] = Integer.parseInt(parts[1].split(",")[0].trim());
				window[1] = Integer.parseInt(parts[1].split(",")[1].trim());
				break;
			}
		}
		main.setLayout(null);
		main.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				nodeList.forEach(element -> {
					int newWidth = (int)(main.getWidth() * element.percentInfo[0] / 100 + element.pxInfo[0]);
					int newHeight = (int)(main.getHeight() * element.percentInfo[1] / 100 + element.pxInfo[1]);
					int newX = (int)(main.getWidth() * element.percentInfo[2] / 100 + element.pxInfo[2]);
					int newY = (int)(main.getHeight() * element.percentInfo[3] / 100 + element.pxInfo[3]);
					element.main.setBounds(newX, newY, newWidth, newHeight);
				});
			}
		});
		mainFrame.add(main);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(window[0], window[1]);
		mainFrame.setVisible(true);
	}
	
}
