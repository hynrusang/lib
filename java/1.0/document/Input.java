package document;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

final public class Input extends HTMLElement<Input> {
	private boolean lock;
	private String placeholder;
	private String current;
	public Input() {
		this("");
	}
	public Input(String placeholder) {
		super(new JTextField());
		lock = false;
		this.placeholder = placeholder;
		this.current = "";
		((JTextField)main).setText(placeholder);
		((JTextField)main).getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
            	if (!lock) SwingUtilities.invokeLater(() -> {
            		lock = true;
            		try {
            			String text = e.getDocument().getText(0, e.getDocument().getLength());
            			if (current.equals("")) {
            				current = text.replaceAll(placeholder, "");
            				((JTextField)main).setText(current);
            			} else current = text;
            		} catch (Exception ex) { ex.printStackTrace(); }
            		lock = false;
            	});
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            	if (!lock) SwingUtilities.invokeLater(() -> {
            		lock = true;
            		try {
            			String text = e.getDocument().getText(0, e.getDocument().getLength());
            			System.out.println(text);
            			if (text.isEmpty()) {
            				((JTextField)main).setText(placeholder);
            				((JTextField)main).setCaretPosition(0);
            			}
            			current = text;
            			System.out.println(current);
            		} catch (Exception ex) { ex.printStackTrace(); }
            		lock = false;
            	});
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // 스타일 변경 시 호출됨 (일반적으로 텍스트 컴포넌트에서 사용되지 않음)
            }
        });
	}
}
