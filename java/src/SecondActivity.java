import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import view.Activity;

public class SecondActivity extends Activity {
	protected SecondActivity() {
		super("");
		JPanel topPanel = new JPanel();
		top(topPanel);
		
		JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(scrollPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
        
        main.add(scrollPane, BorderLayout.CENTER);
        
        bottom(new JPanel());
	}

}
