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

public class MainActivity extends Activity {
	public MainActivity() {
		super("");
		JPanel topPanel = new JPanel();
		top(topPanel);
		
		JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(scrollPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
        
        main.add(scrollPane, BorderLayout.CENTER);
        
        for (int i = 0; i < 100; i++) {
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());

            // JLabel 추가
            JLabel label = new JLabel("Label " + i);
            panel.add(label, BorderLayout.WEST);

            // JButton 추가
            JButton button1 = new JButton("Button");
            button1.setPreferredSize(new Dimension(80, 40)); // 40x100%
            button1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					intent(R.activity_second, null);
				}
            });

            // JPanel에 버튼 추가
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(1, 2));
            buttonPanel.add(button1);

            panel.add(buttonPanel, BorderLayout.EAST);

            scrollPanel.add(panel);
        }
        
        bottom(new JPanel());
	}

}
