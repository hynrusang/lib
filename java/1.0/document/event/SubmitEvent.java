package document.event;
import java.util.ArrayList;
import document.Form;
import document.HTMLElement;

public interface SubmitEvent {
	abstract public Form getTarget();
	abstract public ArrayList<HTMLElement<?>> getFormData();
}
