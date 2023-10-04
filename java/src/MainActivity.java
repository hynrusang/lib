import java.awt.Color;
import util.Livedata;
import util.Document.Button;
import util.Document.Div;
import util.Document.Fragment;
import util.Document.Html;

public class MainActivity {
	private static String target = "Document";
	public static void main(String[] args) {
    	switch (MainActivity.target) {
    	case "Livedata":
    		Livedata<Integer> livedata = new Livedata<Integer>(0);
            livedata.observe(it -> {
                System.out.println("data was changed:" + it);
            });
            livedata.setValue(3);
            livedata.setValue(7);
            livedata.setValue(7);
            livedata.setValue(9);
    		break;
    	case "Document":
    		Html html = new Html("window: 720, 440");
    		Div footer = new Div(
    			new Button("first fragment").style.size(20, 0, 100, 0).end()
    		).style.size(100, 0, 0, 60).position(0, 0, 100, -60).background(Color.GRAY).end();
    		Fragment first = new Fragment(
    			new Div().style.size(100, 0, 0, 60).background(Color.RED).end(),
    			new Div().style.size(100, 0, 0, 60).position(0, 0, 0, 60).background(Color.GREEN).end(),
    			new Div().style.size(100, 0, 100, -180).position(0, 0, 0, 120).background(Color.YELLOW).end(),
    			footer
    		);
    		html.onCreate(first);
    		break;
    	}
    }
}
