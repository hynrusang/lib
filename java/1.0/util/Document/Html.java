package util.Document;
import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Html extends JFrame {
	private JPanel body;
	private ArrayList<Component> elements;
	
	public void onCreate(Fragment bundle) {
		if (!getTitle().equals(bundle.title)) {
			setTitle(bundle.title);
			elements = bundle.elements;
			
			body.removeAll();
			elements.forEach(element -> body.add(element));
			body.dispatchEvent(new ComponentEvent(body, ComponentEvent.COMPONENT_RESIZED));
		}
	}
	public Html() {
		body = new JPanel();
		elements = new ArrayList<Component>();
		
		body.setLayout(null);
		body.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                elements.forEach(element -> {
                	if (element instanceof HTMLElement) {
                		int[] percentInfo = ((HTMLElement)element).getPercentInfo();
                		int[] pxInfo = ((HTMLElement)element).getPxInfo();
                		int newWidth = body.getWidth() * percentInfo[0] / 100 + pxInfo[0];
                		int newHeight = body.getHeight() * percentInfo[1] / 100 + pxInfo[1];
                		int newX = body.getWidth() * percentInfo[2] / 100 + pxInfo[2];
                		int newY = body.getHeight() * percentInfo[3] / 100 + pxInfo[3];
                		element.setBounds(newX + getX(), newY + getY(), newWidth, newHeight);
                	}
                });
            }
        });
		
		add(body);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1040, 700);
		setVisible(true);
	}
}