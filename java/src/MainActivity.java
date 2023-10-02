import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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
    		Fragment side = new Fragment(target).customize.background(Color.BLUE).end();
    		side.addComponentListener(new ComponentAdapter() {
    			@Override
    			public void componentResized(ComponentEvent e) {
    				Container parent = e.getComponent().getParent();
    				e.getComponent().setSize(parent.getWidth() / 2, 60);
    			}
    		});
    		Fragment first = new Fragment(null,
    			side
    		).customize.layout(new FlowLayout(FlowLayout.LEFT)).background(Color.GRAY).end();
    		
    		html.onCreate(first);
    		break;
    	}
    }
}
