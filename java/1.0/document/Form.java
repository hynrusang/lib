package document;
import javax.swing.JPanel;
import document.event.SubmitListener;

final public class Form extends HTMLElement<Form> {
	protected SubmitListener listener;
	
	public Form onSubmit(SubmitListener listener) {
		this.listener = listener;
		return this;
	}
	
	public Form(HTMLElement<?>... elements) {
		super(new JPanel(), elements);
		nodeList.forEach(element -> {
			if (element instanceof Submit) ((Submit)element).form = Form.this;
		});
	}
}