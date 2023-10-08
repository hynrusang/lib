package document;

public class ScrollBox extends HTMLElement<ScrollBox> {
    private JPanel mainPanel;
    public ScrollBox(int shellCount, HTMLElement<?>... elements) {
        super(new JScrollPane());
        // main.setLayout(new ScrollPaneLayout());
        mainPanel = new JPanel();
    }
}
