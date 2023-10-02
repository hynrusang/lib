package util.Document;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Html extends JFrame {
	private ArrayList<Component> children = new ArrayList<Component>();
	private JLabel head;
	private Fragment body;
	
	public void appendChild(Component component) {
		add(component);
		children.add(component);
	}
	public void appendChild(Component component, Object constraints) {
		add(component, constraints);
		children.add(component);
	}
	public void removeChild(Component component) {
		remove(component);
		children.remove(component);
	}
	public void onCreate(Fragment bundle) {
		head.setText("document: " + (bundle.getName() != null ? bundle.getName() : "index"));
		
		if (body != null) removeChild(body);
		appendChild(bundle, BorderLayout.CENTER);
		body = bundle;
		repaint();
	}
	public Html() { 
		head = new JLabel();
		appendChild(head, BorderLayout.NORTH);
		addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				children.forEach(child -> child.dispatchEvent(new ComponentEvent(child, ComponentEvent.COMPONENT_RESIZED)));
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				children.forEach(child -> child.dispatchEvent(new ComponentEvent(child, ComponentEvent.COMPONENT_MOVED)));
			}

			@Override
			public void componentShown(ComponentEvent e) {
				children.forEach(child -> child.dispatchEvent(new ComponentEvent(child, ComponentEvent.COMPONENT_SHOWN)));
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				children.forEach(child -> child.dispatchEvent(new ComponentEvent(child, ComponentEvent.COMPONENT_HIDDEN)));
			}
		});
		setVisible(true);
	};
	public Html(String headers) {
		this();
		for (String header: headers.split(";")) {
			String[] spliter = header.split(":")[1].split(",");
			if (header.indexOf("font") != -1) head.setFont(new Font(spliter[0].trim(), Font.PLAIN, Integer.parseInt(spliter[1].trim())));
			else if (header.indexOf("windows") != -1) setSize(Integer.parseInt(spliter[0].trim()), Integer.parseInt(spliter[1].trim()));
		}
	}
}
