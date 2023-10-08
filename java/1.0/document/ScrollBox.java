package document;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;

public class ScrollBox extends HTMLElement<ScrollBox> {
    private JPanel mainPanel;
    public ScrollBox(int shellCount, HTMLElement<?>... elements) {
        super(new JScrollPane());
        main.setLayout(new ScrollPaneLayout());
        mainPanel = new JPanel(new GridLayout(0,shellCount));
        for (HTMLElement<?> element: elements) {
        	nodeList.add(element);
        	mainPanel.add(element.main);
        }
        ((JScrollPane)main).setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        ((JScrollPane)main).setViewportView(mainPanel);
    }
}
