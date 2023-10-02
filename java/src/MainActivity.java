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
    			new Fragment(null).customize.size("100%", "60px").position("0px", "0px").background(Color.RED).end(),
    			new Fragment(null).customize.size(20, 0, 100, -120).position("0px", "60px").background(Color.GREEN).end(),
    			new Fragment(null).customize.size(60, 0, 100, -120).position("20%", "60px").background(Color.LIGHT_GRAY).end(),
    			new Fragment(null).customize.size(20, 0, 100, -120).position("80%", "60px").background(Color.BLUE).end(),
    			new Fragment(null).customize.size("100%", "60%").position(0, 0, 80, 40).background(Color.PINK).end()
    		).customize.layout(new FlowLayout(FlowLayout.LEFT)).end();
    		html.onCreate(first);
    		break;
    	}
    }
}
