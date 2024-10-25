package document;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

final public class Input extends HTMLElement<Input> {
	private boolean lock;

	public Input onEnter(ActionListener listener) {
		((JTextField)main).addActionListener(listener);
		return this;
	}
	
	public Input() {
		this("");
	}
	public Input(String placeholder) {
		super(new JTextField(placeholder));
		lock = false;
		text = "";
		
		((JTextField)main).getDocument().addDocumentListener(new DocumentListener() {
			@Override
            public void insertUpdate(DocumentEvent e) {
            	if (!lock) SwingUtilities.invokeLater(() -> {
            		lock = true;
            		try {
            			String buffer = e.getDocument().getText(0, e.getDocument().getLength());
            			if (text.isEmpty()) {
            				buffer = buffer.replaceAll(placeholder, "");
            				((JTextField)main).setText(buffer);
            			}
            			text = buffer;
            		} catch (Exception ex) { ex.printStackTrace(); }
            		lock = false;
            	});
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            	if (!lock) SwingUtilities.invokeLater(() -> {
            		lock = true;
            		try {
            			String buffer = e.getDocument().getText(0, e.getDocument().getLength());
            			if (buffer.isEmpty()) {
            				((JTextField)main).setText(placeholder);
            				((JTextField)main).setCaretPosition(0);
            			}
            			text = buffer;
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
					if (text.isEmpty()) ((JTextField)main).setCaretPosition(0);
					lock = false;
				}
			}
		});
	}
}
