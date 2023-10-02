import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;

import util.Livedata;
import util.Document.Body;
import util.Document.Fragment;
import util.Document.Head;
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
    		new Html(
    			new Head(null, new String[] {
    				"url-font: Arial, 20"
    			}),
    			new Body(
    				new Fragment(
    		        	new JButton("bt1"), 
    		            new JButton("bt2"), 
    		            new JButton("bt3")
    		        ).withStyle.layout(new GridLayout(3, 1, 1, 10)).end()
    			).withStyle.layout(new FlowLayout(FlowLayout.LEFT)).background(Color.GRAY).end()
    		).withStyle.size(1040, 720).end();
    		break;
    	}
    }
}
