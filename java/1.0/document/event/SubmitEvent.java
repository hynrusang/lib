package document.event;
import javax.swing.JTextField;
import document.Form;

public interface SubmitEvent {
	abstract public Form getTarget();
	abstract public JTextField[] getFormData();
}
