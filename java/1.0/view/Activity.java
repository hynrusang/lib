package view;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class Activity {
	private int[] window;
	private JFrame mainFrame;
	protected Object[] intent;
	protected JPanel main;
	
	abstract protected void handlingIntent();
	final protected void top(JComponent component) {
		top(component, 40);
	}
	final protected void top(JComponent component, int size) {
		component.setPreferredSize(new Dimension(mainFrame.getWidth(), size));
		main.add(component, BorderLayout.NORTH);
	}
	final protected void bottom(JComponent component) {
		bottom(component, 40);
	}
	final protected void bottom(JComponent component, int size) {
		component.setPreferredSize(new Dimension(mainFrame.getWidth(), size));
		main.add(component, BorderLayout.SOUTH);
	}
	final public Activity launch() {
		mainFrame.setVisible(true);
		return this;
	}
	final public void intent(Activity activity, Object... intent) {
		mainFrame.dispose();
		activity.intent = intent;
		activity.handlingIntent();
		activity.launch();
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
