package restful;

import org.json.JSONObject;

import java.util.*;

public class NoiseMain {
    private MakeFutureHistory futurehistory = new MakeFutureHistory();
    public String gaussian = "gaussian";
    public String straight_fast = "straight_fast"; // 60 per minute
    public String straight_slow = "straight_slow"; // 40 per minute
    public String strategy = gaussian;

    private Map<String, Noise> map = new HashMap<String, Noise>();
    private int limit = 20;
    private int count = 0;
    private long beginTime;
    private int result_count= 0;

    public NoiseMain() {
        System.out.println("The choosen stratigy is " + strategy);

    }


    public double getTimeToWait() {
        double result = 0;
        if ( strategy == gaussian) {
            result =  futurehistory.getGaussianRandomSeed(); //
        } else if ( strategy == straight_fast) {
            result = 1000; // every second : 60rpm
        } else if ( strategy == straight_slow) {
            result = 1500; // every 1.5 second : 40rpm
        }
        return result;

    }

    public void setup() {
        beginTime = System.currentTimeMillis();
        for ( int i = 0; i < 5; i++ ) {
            String id = i + "";
            Noise t = new Noise(id, this);
            map.put(id,t);
        }

        for ( String k : map.keySet()) {
            map.get(k).start();
        }
        System.out.println( "result_count|id|question_milsec|count|answer_milsec" );
    }
    public void show(String result ) {
        result_count++;
        JSONObject obj = new JSONObject(result);
        String id = obj.getString("id");
        String question = obj.getString("pause_question");
        String count = obj.getString("count");
        int answer = obj.getInt("pause_answer");

        System.out.println( result_count + "|" + id + "|" + question  + "|" + count  + "|" + answer );
    }
    public boolean turnstile(String id) {
        count++;
        boolean keepAlive = false;
        if ( count < limit ) {
            keepAlive = true;
        } else {
            map.remove(id);
            System.out.println("Removing " + id + " ( " + map.size() + " threads left )");
        }

        if ( map.size() == 0 ) {
            long endTime = System.currentTimeMillis() - beginTime;
            double seconds = endTime * .001;
            System.out.println("The end in " + seconds + " sec " ) ;
        }
        return keepAlive;
    }

    public static void main(String...args) {
        NoiseMain noiseMaker = new NoiseMain();
        noiseMaker.setup();
    }
}