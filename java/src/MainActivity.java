import util.Livedata;
import document.Html;

public class MainActivity {
	private static String target = "Document";
	public static void main(String[] args) {
    	switch (MainActivity.target) {
    	case "Livedata":
    		Livedata<Integer> livedata = new Livedata<Integer>(0);
    		livedata.observe(it -> System.out.println("data was changed:" + it));
    		livedata.setValue(3);
    		livedata.setValue(7);
    		livedata.setValue(7);
    		livedata.setValue(9);
    		break;
    	case "Document":
    		Html.init(R.activity_main, R.activity_second);
    		break;
    	}
    }
}
