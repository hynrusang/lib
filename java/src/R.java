import java.awt.Color;

import document.Button;
import document.Div;
import document.Fragment;
import document.Html;

public class R {
	public static Fragment activity_main = new Fragment(
		new Div().size(100, 0, 0, 60).background(Color.RED),
		new Div(
			new Button("hello, world!").size(50, 0, 50, 0).position(25, 0, 25, 0)
		).size(100, 0, 100, -120).position(0, 0, 0, 60).background(Color.YELLOW),
		new Div(
			new Button("first").size(20, 0, 100, 0).onClick(target -> {
				Html.swiping(R.activity_main);
			}),
			new Button("second").size(20, 0, 100, 0).position(20, 0, 0, 0).onClick(target -> {
				Html.swiping(R.activity_second);
			})
		).size(100, 0, 0, 60).position(0, 0, 100, -60)
	);
	public static Fragment activity_second = new Fragment("index 2",
		new Div().size(100, 0, 0, 60).background(Color.RED),
		new Div(
			new Button("hello, world 2!").size(50, 0, 50, 0).position(25, 0, 25, 0)
		).size(100, 0, 100, -120).position(0, 0, 0, 60).background(Color.YELLOW),
		new Div(
			new Button("first").size(20, 0, 100, 0).onClick(target -> {
				Html.swiping(R.activity_main);
			}),
			new Button("second").size(20, 0, 100, 0).position(20, 0, 0, 0).onClick(target -> {
				Html.swiping(R.activity_second);
			})
		).size(100, 0, 0, 60).position(0, 0, 100, -60)
	);
}
