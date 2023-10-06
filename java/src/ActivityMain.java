import java.awt.Color;

import document.Button;
import document.Div;
import document.Fragment;
import document.Html;

public class ActivityMain extends Fragment {
	public ActivityMain() {
		super(
			new Div().size(100, 0, 0, 60).background(Color.RED),
			new Div(
				new Button("hello, world!").size(50, 0, 50, 0).position(25, 0, 25, 0)
			).size(100, 0, 100, -120).position(0, 0, 0, 60).background(Color.YELLOW),
			new Div(
				new Button("first fragment").size(20, 0, 100, 0).onClick(target -> {
					Html.swiping(R.activity_main);
				})
			).size(100, 0, 0, 60).position(0, 0, 100, -60)
		);
	}
}
