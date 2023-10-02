package util.Document;
import java.awt.Component;
import javax.swing.JPanel;

public class Fragment extends JPanel {
	private String name;
	public String getName() {
		return name;
	}
	public Fragment(String name, Component... components) {
		this.name = name;
		for (Component component: components) add(component);
	}
}
