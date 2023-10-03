import java.awt.Color;

import javax.swing.JPanel;

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
    		Html html = new Html();
    		Div div = new Div().style.size(10, 0, 50, 0).end();
    		div.setBackground(Color.RED);
    		Fragment first = new Fragment(div);
    		
    		html.onCreate(first);
    		break;
    	}
    }
}
