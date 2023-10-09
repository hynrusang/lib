package view;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class Activity {
	private int[] window;
	private Object[] intent;
	private JFrame mainFrame;
	protected JPanel main;
	
	final protected void top(JComponent component) {
		top(component, mainFrame.getWidth(), 40);
	}
	final protected void top(JComponent component, int width, int height) {
		component.setPreferredSize(new Dimension(width, height));
		main.add(component, BorderLayout.NORTH);
	}
	final protected void bottom(JComponent component) {
		bottom(component, mainFrame.getWidth(), 40);
	}
	final protected void bottom(JComponent component, int width, int height) {
		component.setPreferredSize(new Dimension(width, height));
		main.add(component, BorderLayout.SOUTH);
	}
	final public Activity launch() {
		mainFrame.setVisible(true);
		return this;
	}
	final public Activity intent(Activity activity, Object... intent) {
		mainFrame.dispose();
		activity.intent = intent;
		return activity.launch();
	}
	protected Activity(String headers, Fragment... fragments) {
		window = new int[] { 1040, 720 };
		intent = new Object[0];
		mainFrame = new JFrame();
		main = new JPanel(new BorderLayout());
		
		for (String header: headers.split(";")) {
			String[] token = header.split(":");
			switch (token[0]) {
			case "window":
				window[0] = Integer.parseInt(token[1].split(",")[0].trim());
				window[1] = Integer.parseInt(token[1].split(",")[1].trim());
				break;
			}
		}
		mainFrame.setSize(window[0], window[1]);
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainFrame.add(main);
	}
}
