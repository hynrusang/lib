package util.Document;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Html extends JFrame {
	private JPanel body;
	private ArrayList<Component> elements;
	private static ComponentAdapter resizeListener;
	
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
		resizeListener = new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int parentWidth = body.getWidth();
                int parentHeight = body.getHeight();

                int childWidth = (int) (parentWidth * 0.5); // 예시: 부모의 50%
                int childHeight = (int) (parentHeight * 0.5); // 예시: 부모의 50%
                
                elements.forEach(element -> {
                	if (element instanceof HTMLElement) {
                		int[] percentInfo = ((HTMLElement)element).getPercentInfo();
                		int[] pxInfo = ((HTMLElement)element).getPxInfo();
                		int newWidth = body.getWidth() * percentInfo[0] / 100 + pxInfo[0];
                		int newHeight = body.getHeight() * percentInfo[1] / 100 + pxInfo[1];
                		element.setBounds(0, 0, newWidth, newHeight);
                	}
                });
            }
        };
		
		body.setLayout(null);
		body.addComponentListener(resizeListener);
		
		getContentPane().add(body);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1040, 700);
		setVisible(true);
	}
}