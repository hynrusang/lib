import util.Document.Button;
import util.Document.Div;
import util.Document.Html;

public class Util {
	public static Div footer = new Div(
		new Button("first fragment").size(20, 0, 100, 0).onClick(e -> {
			Html.onCreate(new ActivityMain());
		})
	).size(100, 0, 0, 60).position(0, 0, 100, -60);
}
