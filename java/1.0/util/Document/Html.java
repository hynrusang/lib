package util.Document;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Html extends JFrame {
	private static final long serialVersionUID = 1L;
	private int windowx;
	private int windowy;
	private JPanel body;
	private ArrayList<HTMLElement<?>> nodeList;
	
	public void onCreate(Fragment bundle) {
		if (!getTitle().equals(bundle.title)) {
			setTitle(bundle.title);
			nodeList = bundle.nodeList;
			
			body.removeAll();
			nodeList.forEach(element -> body.add(element.main));
			body.dispatchEvent(new ComponentEvent(body, ComponentEvent.COMPONENT_RESIZED));
			repaint();
		}
	}
	public Html() {
		this("");
	}
	public Html(String headers) {
		windowx = 1040;
		windowy = 720;
		body = new JPanel();
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
		body.setLayout(null);
		body.addComponentListener(new ComponentAdapter() {
			@Override
            public void componentResized(ComponentEvent e) {
                nodeList.forEach(element -> {
                	float[] percentInfo = element.percentInfo;
					int[] pxInfo = element.pxInfo;
            		int newWidth = (int)(body.getWidth() * percentInfo[0] / 100 + pxInfo[0]);
            		int newHeight = (int)(body.getHeight() * percentInfo[1] / 100 + pxInfo[1]);
            		int newX = (int)(body.getWidth() * percentInfo[2] / 100 + pxInfo[2]);
            		int newY = (int)(body.getHeight() * percentInfo[3] / 100 + pxInfo[3]);
            		element.main.setBounds(newX, newY, newWidth, newHeight);
                });
            }
        });
		
		add(body);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(windowx, windowy);
		setVisible(true);
	}
}