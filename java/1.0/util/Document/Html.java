package util.Document;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Html extends JFrame {
	private static final long serialVersionUID = 1L;
	private static JFrame html;
	private static JLabel head;
	private static Fragment body;
	private static void reloadTitle() {
		head.setText("document: " + (body.getName() != null ? body.getName() : "index"));
	}
	public static void onCreate(String headers, Fragment bundle) {
		if (html != null) throw new RuntimeException("html was already created.");
		html = new JFrame();
		head = new JLabel();
		body = bundle;
		
		if (headers != null) for (String header: headers.split(";")) {
			String[] spliter = Arrays.stream(header.split(":")[1].split(",")).map(String::trim).toArray(String[]::new);
			if (header.indexOf("font") != -1) head.setFont(new Font(spliter[0], Font.PLAIN, Integer.parseInt(spliter[1])));
			else if (header.indexOf("windows") != -1) html.setSize(Integer.parseInt(spliter[0]), Integer.parseInt(spliter[1]));
		}
		reloadTitle();
		
		html.add(head, BorderLayout.NORTH);
		html.add(body, BorderLayout.CENTER);
		html.setVisible(true);
	}
	public static void onReplace(Fragment bundle) {
		html.remove(body);
		body = bundle;
		
		reloadTitle();
		html.add(bundle);
		html.repaint();
	}
}
