package util.Document;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Html {
	private static int windowx;
	private static int windowy;
	private static JFrame mainFrame;
	private static JPanel main;
	private static ArrayList<HTMLElement<?>> nodeList;

	public static void find(HTMLElement<?> root) {
		for (HTMLElement<?> element: root.nodeList) {
			System.out.println(element.getClass().getName());
			if (!element.nodeList.isEmpty()) find(element);
		};
	}
	public static void onCreate(Fragment bundle) {
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
	public static void setHeader(String headers) {
		windowx = 1040;
		windowy = 720;
		mainFrame = new JFrame();
		main = new JPanel();
		nodeList = new ArrayList<HTMLElement<?>>();
		
		for (String header: headers.split(";")) {
			String[] parts = header.split(":");
			switch (parts[0].trim()) {
			case "window":
				windowx = Integer.parseInt(parts[1].split(",")[0].trim());
				windowy = Integer.parseInt(parts[1].split(",")[1].trim());
				break;
			}
		}
		main.setLayout(null);
		main.addComponentListener(new ComponentAdapter() {
			@Override
            public void componentResized(ComponentEvent e) {
				nodeList.forEach(element -> {
					float[] percentInfo = element.percentInfo;
            		int[] pxInfo = element.pxInfo;
            		int newWidth = (int)(main.getWidth() * percentInfo[0] / 100 + pxInfo[0]);
            		int newHeight = (int)(main.getHeight() * percentInfo[1] / 100 + pxInfo[1]);
            		int newX = (int)(main.getWidth() * percentInfo[2] / 100 + pxInfo[2]);
            		int newY = (int)(main.getHeight() * percentInfo[3] / 100 + pxInfo[3]);
            		element.main.setBounds(newX, newY, newWidth, newHeight);
                });
            }
        });
		mainFrame.add(main);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(windowx, windowy);
		mainFrame.setVisible(true);
	}
	
}