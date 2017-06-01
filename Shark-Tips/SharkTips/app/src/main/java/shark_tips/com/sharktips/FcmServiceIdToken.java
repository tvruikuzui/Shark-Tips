package shark_tips.com.sharktips;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by liranelyadumi on 5/8/17.
 */

public class FcmServiceIdToken extends FirebaseInstanceIdService {

     private String checkUserEmailExsist,refreshedToken;
    static boolean paid = true;

    public static void setPaid(boolean paid) {
        FcmServiceIdToken.paid = paid;
    }


    @Override
    public void onTokenRefresh() {
        if (paid) {
            refreshedToken = FirebaseInstanceId.getInstance().getToken();
            Log.d("TOKEN", "Refreshed token: " + refreshedToken);
            checkUserEmailExsist = MyHelper.getUserEmailFromSharedPreferences(this);
            if (checkUserEmailExsist.isEmpty())
                return;
            sendNewToken(checkUserEmailExsist, refreshedToken);
        }
    }

    private void sendNewToken(String checkUserEmailExsist, String refreshedToken) {

        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                HttpURLConnection urlConnection = null;
                OutputStream outputStream = null;
                InputStream inputStream = null;
                String result = "";
                try {
                    URL url = new URL("http://35.184.144.226/shark2/token/" + params[0] + "/");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setUseCaches(false);
                    urlConnection.setDoOutput(true);
                    urlConnection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
                    urlConnection.connect();
                    outputStream = urlConnection.getOutputStream();
                    outputStream.write(params[1].getBytes());
                    outputStream.close();
                    inputStream = urlConnection.getInputStream();
                    byte [] buffer = new byte[128];
                    int actuallyRead = inputStream.read(buffer);
                    result = new String(buffer,0,actuallyRead);
                    inputStream.close();
                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
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
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                switch (s){
                    case "ok":
                        Log.d("REFRESH_TOKEN",s);
                        break;
                    case "error":
                        Log.d("REFRESH_TOKEN",s);
                        break;
                }
            }
        }.execute(checkUserEmailExsist,refreshedToken);
    }
}
