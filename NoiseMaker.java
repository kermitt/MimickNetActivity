package restful;

import org.json.JSONObject;

import java.util.*;

public class NoiseMaker {
    private Map<String, Noise> map = new HashMap<String, Noise>();
    private int limit = 20;
    private int count = 0;
    private long beginTime;
    private int result_count= 0;

    private double _gaussianRandomSeed(int lowBound, int highBound ) {
        double r = 0;
        int  loops = 6;
        for (int i = 0; i < loops; i += 1) {
            r += Math.random();
        }
        r /= loops;
        double seed = Math.floor(lowBound + r * ( highBound - lowBound + 1));
        return seed;
    }

    protected double getTimeToWait() {
        // In reality, our speeds tend to run 0.7 sec to 1.5 but 1-in-a-100 will be 5 or more seconds...
        double seed = _gaussianRandomSeed(0,4);
        double r = Math.random();
        double scalar = 999;
        if ( seed == 1.0 ) {
            scalar = 500;
        } else if ( seed == 2.0 ) {
            scalar = 1500;
        } else if ( seed == 3.0 ) {

            if ( Math.random() > 0.9 ) {

                scalar = 10000;
            } else {
                scalar = 1000;
            }
        }
        return (Math.random() * scalar);
    }

    public NoiseMaker() {}

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
            //System.out.println("keepAlive  " + id + " with " + map.size());
        } else {
            map.remove(id);
            System.out.println("Removing " + id + " with " + map.size());
        }

        if ( map.size() == 0 ) {
            long endTime = System.currentTimeMillis() - beginTime;
            double seconds = endTime * .001;
            System.out.println("The end in " + seconds + " sec " ) ;
        }
        return keepAlive;
    }

    public static void main(String...args) {
        NoiseMaker noiseMaker = new NoiseMaker();
        noiseMaker.setup();
    }
}
