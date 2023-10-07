import java.awt.Color;
import javax.swing.JTextField;
import document.*;

public class R {
	public static Fragment activity_main = new Fragment(
		new Div().size(100, 0, 0, 60).background(Color.RED),
		new Form(
			new Input("placeholder").size(50, 0, 0, 40).position(25, 0, 0, 40),
			new Button("submit").size(0, 100, 0, 40).position(50, -50, 0, 80)
		).size(100, 0, 100, -120).identity("true").position(0, 0, 0, 60).background(Color.YELLOW).onSubmit(e -> {
			for (HTMLElement<?> field: e.getFormData()) {
				if (field instanceof Input) System.out.println(((Input)field).getValue());
			}
		}),
		new Div(
			new Button("first").size(20, 0, 100, 0).onClick(e -> {
				Html.replace(R.activity_main);
				System.out.println(Html.findAll("tag:Button"));
				System.out.println(Html.find("identity:true"));
			}),
			new Button("second").size(20, 0, 100, 0).position(20, 0, 0, 0).onClick(e -> {
				Html.replace(R.activity_second);
				System.out.println(Html.findAll("tag:Button"));
				System.out.println(Html.find("identity:true"));
			})
		).size(100, 0, 0, 60).position(0, 0, 100, -60)
	);
	
	public static Fragment activity_second = new Fragment("index 2",
		new Div().size(100, 0, 0, 60).background(Color.RED),
		new Div(
			new Button("hello, world 2!").size(50, 0, 50, 0).position(25, 0, 25, 0)
		).size(100, 0, 100, -120).identity("true").position(0, 0, 0, 60).background(Color.YELLOW),
		new Div(
			new Button("first").size(20, 0, 100, 0).onClick(e -> {
				Html.replace(R.activity_main);
			}),
			new Button("second").size(20, 0, 100, 0).position(20, 0, 0, 0).onClick(e -> {
				Html.replace(R.activity_second);
			})
		).size(100, 0, 0, 60).position(0, 0, 100, -60)
	);
}
