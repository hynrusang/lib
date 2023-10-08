import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    public Main() {
        // JFrame 설정
        setSize(1040, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 상단 JPanel
        JPanel topPanel = new JPanel();
        add(topPanel, BorderLayout.WEST);
        topPanel.setPreferredSize(new Dimension(400, getHeight()));

        // 중앙 JScrollPane
        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS)); // 100개의 JPanel을 세로로 배치
        JScrollPane scrollPane = new JScrollPane(scrollPanel);
        scrollPane.setPreferredSize(new Dimension(getWidth(), getHeight() - 80)); // 100% x (100% - 80px)
        add(scrollPane, BorderLayout.CENTER);

        // JScrollPane 안의 100개의 JPanel 추가
        for (int i = 0; i < 100; i++) {
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());

            // JLabel 추가
            JLabel label = new JLabel("Label " + i);
            panel.add(label, BorderLayout.WEST);

            // JButton 추가
            JButton button1 = new JButton("Button");
            button1.setPreferredSize(new Dimension(80, getHeight() / 10)); // 40x100%
            button1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					topPanel.setPreferredSize(new Dimension(0, getHeight()));
					topPanel.revalidate();
					topPanel.repaint();
				}
            });

            // JPanel에 버튼 추가
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(1, 2));
            buttonPanel.add(button1);

            panel.add(buttonPanel, BorderLayout.EAST);

            scrollPanel.add(panel);
        }

        // 하단 JPanel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(getWidth(), 40));
        add(bottomPanel, BorderLayout.SOUTH);

        // 화면 보이기
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}


