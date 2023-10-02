import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JButton;

import util.Livedata;
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
    		Html html = new Html("font: Arial, 24; windows: 1040, 720");
    		Fragment first = new Fragment(null,
    			new Fragment(null).customize.size("40%", "60px").end()
    		).customize.layout(new FlowLayout(FlowLayout.LEFT)).background(Color.GRAY).end();
    		html.onCreate(first);
    		break;
    	}
    }
}
