package restful;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.*;
public class Noise extends Thread {
    private String id;
    private NoiseMaker mother;
    private int count = 0;

    public Noise(String id, NoiseMaker mother) {
        this.id = id;
        this.mother = mother;
    }
    public void run(){
        waitSome_thenSendRequest();
    }
    private void sendMessage(long pause) {
        count++;
        String uri = "http://localhost:3030/pretend?id=" + id + "&pause=" + pause + "&count=" + count;
        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            mother.show(result.toString());
            rd.close();
            waitSome_thenSendRequest();
        } catch( Exception boom ) {
            System.out.println("Error! " + boom.getMessage());
        }
    }
    private void waitSome_thenSendRequest() {
        long pause = (long) mother.getTimeToWait();
        try {
            sleep(pause);
            boolean keepAlive = mother.turnstile(id);
            if (keepAlive) {
                sendMessage(pause);
            }
        } catch (Exception failbot) {
            System.out.println(failbot.getMessage());
        }
    }
}
