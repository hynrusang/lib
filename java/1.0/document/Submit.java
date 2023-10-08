package document;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import document.event.SubmitEvent;

public class Submit extends HTMLElement<Submit> {
	protected Form form;
	
	public Submit() {
		this("submit");
	}
	public Submit(String text) {
		super(new JButton());
		this.text = text;
		((JButton)main).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				form.listener.submitPerform(new SubmitEvent() {
					@Override
					public Form getTarget() {
						return form;
					}

					@Override
					public ArrayList<HTMLElement<?>> getFormData() {
						return form.nodeList;
					}
				});
			}
		});
		((JButton)main).setText(text);
	}
}
