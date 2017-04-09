package shark_tips.com.sharktips;

import android.content.Intent;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by liranelyadumi on 4/6/17.
 */

public class ConnectToServerThread extends Thread {

    private static final String BASE_URL = "http://35.187.25.133/shark1/Users";

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void run() {

        HttpURLConnection urlConnection = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(BASE_URL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setUseCaches(false);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlConnection.connect();
            outputStream = urlConnection.getOutputStream();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("phoneNumber",user.getPhoneNumber());
            jsonObject.put("name",user.getName());
            jsonObject.put("lastName",user.getLastName());
            jsonObject.put("country",user.getCountry());
            jsonObject.put("countryCode",user.getCountryCode());
            jsonObject.put("password",user.getPassword());
            jsonObject.put("email",user.getMail());
            jsonObject.put("admin",user.getIsAdmin());
            outputStream.write(jsonObject.toString().getBytes());
            outputStream.close();
            inputStream = urlConnection.getInputStream();
            byte [] buffer = new byte[128];
            int a = inputStream.read(buffer);
            String result = new String(buffer,0,a);
            Log.d("TAG",result);
            inputStream.close();

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
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (urlConnection != null){
                urlConnection.disconnect();
            }

            //stop();


        }


    }

}
