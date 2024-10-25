import java.awt.Color;
import document.*;

public class R {
	public static Div footer() {
		return new Div(
			new Button("first").size(20, 0, 100, 0).onClick(e -> {
				Html.navigate(0);
			}),
			new Button("second").size(20, 0, 100, 0).position(20, 0, 0, 0).onClick(e -> {
				Html.navigate(1);
			})
		).size(100, 0, 0, 40).position(0, 0, 100, -40);
	}
	
	public static Fragment activity_main = new Fragment(
		new Div(
			new Form(
				new Input("placeholder").size(50, 0, 0, 40).position(25, 0, 0, 20),
				new Submit().size(0, 100, 0, 40).position(50, -50, 0, 60)
			).size(100, 0, 0, 120).background(Color.YELLOW).onSubmit(e -> {
				for (HTMLElement<?> field: e.getFormData()) {
					System.out.println(field.getText());
				}
			}),
			new Div().size(100, 0, 0, 100).background(Color.RED).position(0, 0, 0, 120),
			new Div().size(100, 0, 0, 100).background(Color.ORANGE).position(0, 0, 0, 220),
			new Div().size(100, 0, 0, 100).background(Color.YELLOW).position(0, 0, 0, 320),
			new Div().size(100, 0, 0, 100).background(Color.GREEN).position(0, 0, 0, 420),
			new Div().size(100, 0, 0, 100).background(Color.BLUE).position(0, 0, 0, 520)
		).size(100, 0, 100, -40),
		R.footer()
	);
	
	public static Fragment activity_second = new Fragment("index 2",
		new Div().size(100, 0, 0, 40).background(Color.RED),
		new Div(
			new Button("hello, world 2!").size(50, 0, 50, 0).position(25, 0, 25, 0)
		).size(100, 0, 100, -80).position(0, 0, 0, 40).background(Color.YELLOW),
		R.footer()
	);
}
