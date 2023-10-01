import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;

import util.Livedata;
import util.Document.Body;
import util.Document.Fragment;

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
    		new Body(
    			new Fragment(
    				new Fragment(
    					new JButton[] {
    						new JButton("bt1"), 
    						new JButton("bt2"), 
    						new JButton("bt3")
    					}
    				)
    				.withStyle.layout(new GridLayout(3, 1, 1, 10)).background(Color.blue).end()
    			), "West"
    		).withStyle.title("hello, world!").size(1040, 720).end();
    		break;
    	}
    }
}
