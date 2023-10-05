package util.Document;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Button extends HTMLElement {
	private onClick onClickEvent;
	
	public interface onClick {
		public abstract void action(JButton target);
	}
	
	public Button() {
		super(new JButton());
	}
	public Button(String test) {
		this();
		((JButton)main).setText(test);
	}
	public Button(String test, onClick onClickEvent) {
		this(test);
		this.onClickEvent = onClickEvent;
		((JButton)main).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Button.this.onClickEvent.action((JButton)e.getSource());
			}
		});
	}
}
