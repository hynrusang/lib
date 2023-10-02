package util.Document;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Html extends JFrame {
	private JLabel head;
	private Fragment body;

	public void onCreate(Fragment bundle) {
		head.setText("document: " + (bundle.getName() != null ? bundle.getName() : "index"));
		
		if (body != null) remove(body);
		add(bundle, BorderLayout.CENTER);
		body = bundle;
		repaint();
	}
	public Html() { 
		head = new JLabel();
		add(head, BorderLayout.NORTH);
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
