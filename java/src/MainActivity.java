import Livedata.*;

public class MainActivity {
	private static String target = "LiveData";
    public static void main(String[] args) {
    	if (MainActivity.target.equals("LiveData")) {
    		Livedata<Integer> livedata = new Livedata<Integer>(0);
            livedata.observe(it -> {
                System.out.println("data was changed:" + it);
            });
            livedata.setValue(3);
            livedata.setValue(7);
            livedata.setValue(7);
            livedata.setValue(9);
    	}
    }
}
