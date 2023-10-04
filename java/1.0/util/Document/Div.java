package util.Document;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;

public class Div extends HTMLElement<Div> {
	private JPanel panel;
	public Div(HTMLElement<?>... elements) {
		super(elements);
		main = new JPanel();
		main.setLayout(null);
		
		main.addComponentListener(new ComponentAdapter() {
			@Override
            public void componentResized(ComponentEvent e) {
                Div.this.nodeList.forEach(element -> {
                	int[] percentInfo = element.percentInfo;
            		int[] pxInfo = element.pxInfo;
            		int newWidth = Div.this.main.getWidth() * percentInfo[0] / 100 + pxInfo[0];
            		int newHeight = Div.this.main.getHeight() * percentInfo[1] / 100 + pxInfo[1];
            		int newX = Div.this.main.getWidth() * percentInfo[2] / 100 + pxInfo[2];
            		int newY = Div.this.main.getHeight() * percentInfo[3] / 100 + pxInfo[3];
            		element.main.setBounds(newX, newY, newWidth, newHeight);
                });
            }
        });
	}
}
