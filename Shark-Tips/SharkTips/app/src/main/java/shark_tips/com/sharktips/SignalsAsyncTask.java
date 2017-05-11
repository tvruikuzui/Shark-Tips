package shark_tips.com.sharktips;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by User on 11/05/2017.
 */

public class SignalsAsyncTask extends AsyncTask<Signal,Void,String>{

    private boolean shouldUpdate;
    private Context c;
    private Signal s;

    public void setS(Signal s) {
        this.s = s;
    }

    public void setShouldUpdate(boolean shouldUpdate) {
        this.shouldUpdate = shouldUpdate;
    }

    public void setC(Context c) {
        this.c = c;
    }

    @Override
    protected String doInBackground(Signal... params) {
        String userEmail = MyHelper.getUserEmailFromSharedPreferences(c);
        String userPassword = MyHelper.getUserPasswordFromSharedPreferences(c);
        Signal signal = params[0];
        HttpURLConnection urlConnection = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        String result = "";
        try {
            URL url = new URL("http://35.184.144.226/shark2/admin/"+userEmail+"/"+userPassword);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("PUT");
            urlConnection.setUseCaches(false);
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlConnection.connect();
            outputStream = urlConnection.getOutputStream();
            JSONObject signalObject = new JSONObject();
            if (signal.getId() != null)
                signalObject.put("id",signal.getId());
            signalObject.put("currency",signal.getCurrency());
            signalObject.put("price",signal.getPrice());
            signalObject.put("sellStop",signal.getSellStop());
            signalObject.put("sl",signal.getSl());
            signalObject.put("tp1",signal.getTp1());
            signalObject.put("tp2",signal.getTp2());
            signalObject.put("note",signal.getNote());
            outputStream.write(signalObject.toString().getBytes());
            outputStream.close();
            inputStream = urlConnection.getInputStream();
            byte[] buffer = new byte[64];
            int actuallyRead = inputStream.read(buffer);
            result = new String(buffer,0,actuallyRead);
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
            if (urlConnection != null){
                urlConnection.disconnect();
            }
        }
        return result;
    }
    @Override
    protected void onPostExecute(String result) {
        switch (result){
            case "ok":
                Toast.makeText(c, result, Toast.LENGTH_SHORT).show();
                break;
            case "error":
                Toast.makeText(c, result, Toast.LENGTH_SHORT).show();
        }
    }
}
