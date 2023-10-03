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
	
	public Html() {
		body = new JPanel();
		elements = new ArrayList<Component>();
		elements.add(new JPanel());
		
		body.setLayout(null);
		body.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int parentWidth = body.getWidth();
                int parentHeight = body.getHeight();

                int childWidth = (int) (parentWidth * 0.5); // 예시: 부모의 50%
                int childHeight = (int) (parentHeight * 0.5); // 예시: 부모의 50%
                
                elements.forEach(element -> {
                	element.setSize(childWidth, childHeight);
                });
            }
        });
		
		elements.get(0).setBackground(Color.RED);
		elements.forEach(element -> {
        	body.add(element);
        });
		
		getContentPane().add(body);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1040, 700);
		setVisible(true);
	}
}