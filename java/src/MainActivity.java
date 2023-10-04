import java.awt.Color;
import util.Livedata;
import util.Document.Button;
import util.Document.Div;
import util.Document.Fragment;
import util.Document.Html;

public class MainActivity {
	private static String target = "Document";
	private static Fragment[] fragments;
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
    		fragments = new Fragment[5];
    		Html html = new Html("window: 720, 440");
    		Div footer = new Div(
    			new Button("first fragment", target -> {
    				html.onCreate(MainActivity.fragments[0]);
    			}).style.size(20, 0, 100, 0).end(),
    			new Button("second fragment", target -> {
    				html.onCreate(MainActivity.fragments[1]);
    			}).style.size(20, 0, 100, 0).position(20, 0, 0, 0).end(),
    			new Button("third fragment", target -> {
    				html.onCreate(MainActivity.fragments[2]);
    			}).style.size(20, 0, 100, 0).position(40, 0, 0, 0).end(),
    			new Button("fours fragment", target -> {
    				html.onCreate(MainActivity.fragments[3]);
    			}).style.size(20, 0, 100, 0).position(60, 0, 0, 0).end(),
    			new Button("five fragment", target -> {
    				html.onCreate(MainActivity.fragments[4]);
    			}).style.size(20, 0, 100, 0).position(80, 0, 0, 0).end()
    		).style.size(100, 0, 0, 60).position(0, 0, 100, -60).end();
    		fragments[0] = new Fragment(
    			new Div().style.size(100, 0, 0, 60).background(Color.RED).end(),
    			new Div(
    				new Button("hello, world!").style.size(50, 0, 50, 0).position(25, 0, 25, 0).end()
    			).style.size(100, 0, 100, -120).position(0, 0, 0, 60).background(Color.YELLOW).end(),
    			footer
    		);
    		fragments[1] = new Fragment("index1",
        			new Div().style.size(100, 0, 0, 60).background(Color.RED).end(),
        			new Div(
        				new Button("hello, world 2!").style.size(50, 0, 50, 0).position(25, 0, 25, 0).end()
        			).style.size(100, 0, 100, -120).position(0, 0, 0, 60).background(Color.YELLOW).end(),
        			footer
        		);
    		fragments[2] = new Fragment("index2",
        			new Div().style.size(100, 0, 0, 60).background(Color.RED).end(),
        			new Div(
        				new Button("hello, world 3!").style.size(50, 0, 50, 0).position(25, 0, 25, 0).end()
        			).style.size(100, 0, 100, -120).position(0, 0, 0, 60).background(Color.YELLOW).end(),
        			footer
        		);
    		fragments[3] = new Fragment("index3",
        			new Div().style.size(100, 0, 0, 60).background(Color.RED).end(),
        			new Div(
        				new Button("hello, world 4!").style.size(50, 0, 50, 0).position(25, 0, 25, 0).end()
        			).style.size(100, 0, 100, -120).position(0, 0, 0, 60).background(Color.YELLOW).end(),
        			footer
        		);
    		fragments[4] = new Fragment("index4",
        			new Div().style.size(100, 0, 0, 60).background(Color.RED).end(),
        			new Div(
        				new Button("hello, world 5!").style.size(50, 0, 50, 0).position(25, 0, 25, 0).end()
        			).style.size(100, 0, 100, -120).position(0, 0, 0, 60).background(Color.YELLOW).end(),
        			footer
        		);
    		html.onCreate(fragments[0]);
    		break;
    	}
    }
}
