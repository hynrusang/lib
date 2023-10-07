package document;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

final public class Input extends HTMLElement<Input> {
	private boolean lock;
	private String current;

	@Override
	public String getText() {
		return current;
	}
	public Input() {
		this("");
	}
	public Input(String placeholder) {
		super(new JTextField(placeholder));
		lock = false;
		current = "";
		
		((JTextField)main).getDocument().addDocumentListener(new DocumentListener() {
			@Override
            public void insertUpdate(DocumentEvent e) {
            	if (!lock) SwingUtilities.invokeLater(() -> {
            		lock = true;
            		try {
            			String text = e.getDocument().getText(0, e.getDocument().getLength());
            			if (current.isEmpty()) {
            				text = text.replaceAll(placeholder, "");
            				((JTextField)main).setText(text);
            			}
            			current = text;
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
            			if (text.isEmpty()) {
            				((JTextField)main).setText(placeholder);
            				((JTextField)main).setCaretPosition(0);
            			}
            			current = text;
            		} catch (Exception ex) { ex.printStackTrace(); }
            		lock = false;
            	});
            }

            @Override
            public void changedUpdate(DocumentEvent e) { }
        });
		((JTextField)main).addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				if (!lock) {
					lock = true;
					if (current.isEmpty()) ((JTextField)main).setCaretPosition(0);
					lock = false;
				}
			}
		});
	}
}
