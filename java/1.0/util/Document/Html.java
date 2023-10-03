package util.Document;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Html extends JFrame {
	private static final long serialVersionUID = 1L;
	private JLabel head;
	private Fragment body;
	
	/**
	 * Switch the current fragment to the newly received fragment.
	 * @param bundle Fragment to be transitioned anew
	 */
	public void onCreate(Fragment bundle) {
		head.setText("document: " + (bundle.getId() != null ? bundle.getId() : "index"));
		if (body != null) remove(body);
		add(bundle, BorderLayout.CENTER);
		body = bundle;
		repaint();
	}
	/**
	 * Create the only main component to configure one Windows window.
	 */
	public Html() { 
		head = new JLabel();
		add(head, BorderLayout.NORTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	};
	/**
	 * Create the only main component to configure one Windows window.
	 * @param headers String to be set as one header consisting of a combination of strings
	 */
	public Html(String headers) {
		this();
		for (String header: headers.split(";")) {
			String[] spliter = header.split(":")[1].split(",");
			if (header.indexOf("font") != -1) head.setFont(new Font(spliter[0].trim(), Font.PLAIN, Integer.parseInt(spliter[1].trim())));
			else if (header.indexOf("windows") != -1) setSize(Integer.parseInt(spliter[0].trim()), Integer.parseInt(spliter[1].trim()));
		}
	}
}
