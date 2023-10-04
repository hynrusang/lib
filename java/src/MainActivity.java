import java.awt.Color;

import util.Livedata;
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
    		Fragment first = new Fragment(
    			new Div().style.size(100, 0, 0, 60).background(Color.RED).end(),
    			new Div().style.size(100, 0, 0, 60).position(0, 0, 0, 60).background(Color.GREEN).end(),
    			new Div(
    				new Div().style.size(50, 0, 50, 0).position(25, 0, 25, 0).background(Color.BLACK).end()
    			).style.size(100, -300, 50, -90).position(0, 0, 0, 120).background(Color.BLUE).end(),
    			new Div().style.size(100, -300, 50, -90).position(0, 0, 50, 30).background(Color.YELLOW).end(),
    			new Div().style.size(300, 0, 100, -180).position(0, 0, 0, 120).background(Color.GRAY).end(),
    			new Div().style.size(100, 0, 0, 60).position(0, 0, 100, -60).background(Color.ORANGE).end()
    		);
    		
    		html.onCreate(first);
    		break;
    	}
    }
}
