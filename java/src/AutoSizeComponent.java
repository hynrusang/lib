import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AutoSizeComponent extends JFrame {

    private JPanel parentPanel;
    private JPanel childPanel;

    public AutoSizeComponent() {
        parentPanel = new JPanel();
        childPanel = new JPanel();

        // 부모 패널 설정
        parentPanel.setLayout(null); // 자유 배치를 사용하겠다고 설정
        parentPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int parentWidth = parentPanel.getWidth();
                int parentHeight = parentPanel.getHeight();

                int childWidth = (int) (parentWidth * 0.5); // 예시: 부모의 50%
                int childHeight = (int) (parentHeight * 0.5); // 예시: 부모의 50%

                childPanel.setSize(childWidth, childHeight);
            }
        });

        // 자식 패널 설정
        childPanel.setBackground(Color.RED);

        // 부모에 자식 추가
        parentPanel.add(childPanel);

        // 프레임에 부모 패널 추가
        getContentPane().add(parentPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setVisible(true);
    }

    public static void main(String[] args) {
        new AutoSizeComponent();
    }
}