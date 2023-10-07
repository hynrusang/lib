package document;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import document.event.SubmitEvent;
import document.event.SubmitListener;

final public class Form extends HTMLElement<Form> {
	private SubmitListener listener;
	
	public Form onSubmit(SubmitListener listener) {
		this.listener = listener;
		return this;
	}
	public Form(HTMLElement<?>... elements) {
		super(new JPanel(), elements);
		nodeList.forEach(element -> {
			if (element.main instanceof JButton) {
				((JButton)element.main).addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						listener.submitPerform(new SubmitEvent() {
							@Override
							public Form getTarget() {
								return Form.this;
							}

							@Override
							public ArrayList<HTMLElement<?>> getFormData() {
								return nodeList;
							}
						});
					}
				});
			} else if (element.main instanceof JTextField) {
				((JTextField)element.main).addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						listener.submitPerform(new SubmitEvent() {
							@Override
							public Form getTarget() {
								return Form.this;
							}
							
							@Override
							public ArrayList<HTMLElement<?>> getFormData() {
								return nodeList;
							}
						});
					}
				});
			}
		});
	}
}