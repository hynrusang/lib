import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;

import util.Livedata;
import util.Document.Fragment;
import util.Document.Html;

public class MainActivity {
	private static String target = "Document";
    public static void main(String[] args) throws Exception {
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
    		Fragment first = new Fragment(null,
    			new JButton("bt1"),
            	new JButton("bt2"),
            	new JButton("bt3")
    		).customize.layout(new FlowLayout(FlowLayout.LEFT)).background(Color.GRAY).end();
    		
    		Html.onCreate("font: Arial, 24; windows: 1040, 720", first);
    		break;
    	}
    }
}
