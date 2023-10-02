package util.Document;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Html extends JFrame {
	private static final long serialVersionUID = 1L;
	private static String headers;
	public static void setHeader(String header) {
		headers = header;
	}
	public static void onCreate(Fragment bundle) {
		JFrame frame = new JFrame();
		JLabel head = new JLabel();
		
		for (String header: headers.split(";")) {
			String[] spliter = Arrays.stream(header.split(":")[1].split(",")).map(String::trim).toArray(String[]::new);
			if (header.indexOf("font") != -1) head.setFont(new Font(spliter[0], Font.PLAIN, Integer.parseInt(spliter[1])));
			else if (header.indexOf("windows") != -1) frame.setSize(Integer.parseInt(spliter[0]), Integer.parseInt(spliter[1]));
		}
		head.setText("document: " + (bundle.getName() != null ? bundle.getName() : "index"));
		frame.add(head, BorderLayout.NORTH);
		frame.add(bundle, BorderLayout.CENTER);
		frame.setVisible(true);
	}
}
