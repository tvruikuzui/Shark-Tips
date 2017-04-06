package shark_tips.com.sharktips;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by liranelyadumi on 4/6/17.
 */

public class ConnectToServerThread extends Thread {

    private static final String BASE_URL = "";

    @Override
    public void run() {

        HttpURLConnection urlConnection = null;
        OutputStream outputStream = null;
        try {
            URL url = new URL(BASE_URL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setUseCaches(false);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            outputStream = urlConnection.getOutputStream();
            User user = new User();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name",user.getName());
            jsonObject.put("last",user.getLastName());
            jsonObject.put("mail",user.getMail());
            jsonObject.put("phone",user.getPhoneNumber());
            jsonObject.put("password",user.getPassword());
            outputStream.write(jsonObject.toString().getBytes());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            if (outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (urlConnection != null){
                urlConnection.disconnect();
            }
        }
    }
}
