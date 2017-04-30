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
 * Created by liranelyadumi on 4/28/17.
 */

public class GetTokenFromFirebase extends FirebaseInstanceIdService {

    String getEmail = MyHelper.getUserEmailFromSharedPreferences(getBaseContext());


    @Override
    public void onTokenRefresh() {
        final String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("TAG", "Refreshed token: " + refreshedToken);
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                HttpURLConnection urlConnection = null;
                InputStream inputStream = null;
                OutputStream outputStream = null;
                String result = "";
                try {
                    URL url = new URL("http://35.184.144.226/shark2/token/"+params[0]+"/");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);
                    urlConnection.setUseCaches(false);
                    urlConnection.connect();
                    outputStream = urlConnection.getOutputStream();
                    outputStream.write(refreshedToken.toString().getBytes());
                    outputStream.close();
                    inputStream = urlConnection.getInputStream();
                    byte [] buffer = new byte[128];
                    int actuallyRead = inputStream.read(buffer);
                    result = new String(buffer,0,actuallyRead);
                    inputStream.close();
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
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                Log.d("TAG",s);
            }
        }.execute(getEmail,refreshedToken);
    }
}
