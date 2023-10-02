package util.Document;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Html extends JFrame {
	private static ArrayList<Component> children = new ArrayList<Component>();
	private static JFrame html;
	private static JLabel head;
	private static Fragment body;
	
	public static void appendChild(Component component) {
		html.add(component);
		children.add(component);
	}
	public static void appendChild(Component component, Object constraints) {
		html.add(component, constraints);
		children.add(component);
	}
	public static void removeChild(Component component) {
		html.remove(component);
		children.remove(component);
	}
	public static void onCreate(String headers, Fragment bundle) {
		if (html != null) throw new RuntimeException("html was already created.");
		html = new JFrame();
		head = new JLabel();
		body = bundle;
		
		html.addComponentListener(new ComponentListener() {
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
		if (headers != null) for (String header: headers.split(";")) {
			String[] spliter = Arrays.stream(header.split(":")[1].split(",")).map(String::trim).toArray(String[]::new);
			if (header.indexOf("font") != -1) head.setFont(new Font(spliter[0], Font.PLAIN, Integer.parseInt(spliter[1])));
			else if (header.indexOf("windows") != -1) html.setSize(Integer.parseInt(spliter[0]), Integer.parseInt(spliter[1]));
		}
		head.setText("document: " + (body.getName() != null ? body.getName() : "index"));
		
		html.add(head, BorderLayout.NORTH);
		appendChild(body, BorderLayout.CENTER);
		html.setVisible(true);
	}
	public static void onReplace(Fragment bundle) {
		removeChild(body);
		body = bundle;
		
		head.setText("document: " + (body.getName() != null ? body.getName() : "index"));
		
		appendChild(bundle, BorderLayout.CENTER);
		html.repaint();
	}
}
