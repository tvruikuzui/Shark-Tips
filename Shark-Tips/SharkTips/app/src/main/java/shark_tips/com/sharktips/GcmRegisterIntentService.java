package shark_tips.com.sharktips;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static shark_tips.com.sharktips.SignupFragment.BASE_URL;

/**
 * Created by liranelyadumi on 5/1/17.
 */

public class GcmRegisterIntentService extends IntentService {

    public static final String REGISTRATION_OK = "RegistrationOk";
    public static final String REGISTRATION_ERROR = "RegistrationError";

    public GcmRegisterIntentService() {
        super("");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        registerGcm();
    }



    private void registerGcm(){
        Intent registerComplete = null;
        String token = null;
        try {
            InstanceID instanceID = InstanceID.getInstance(getApplicationContext());
            token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE,null);
            Log.d("GCM","token:" + token);

            // UI Upadte
            String userEmail = MyHelper.getUserEmailFromSharedPreferences(this);
            if (!userEmail.isEmpty())
                uploadToServer(token,userEmail);

            registerComplete = new Intent(REGISTRATION_OK);
            registerComplete.putExtra("token",token);

        }catch (Exception e){
            registerComplete = new Intent(REGISTRATION_ERROR);
            Log.d("GCM","ERROR");
        }

        LocalBroadcastManager.getInstance(this).sendBroadcast(registerComplete);
    }

    private void uploadToServer(String token,String userEmail) {

        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {

                HttpURLConnection urlConnection = null;
                OutputStream outputStream = null;
                InputStream inputStream = null;
                String res = null;
                try {
                    URL url = new URL(BASE_URL + "token/" + params[1] + "/");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);
                    urlConnection.setUseCaches(false);
                    urlConnection.connect();
                    outputStream = urlConnection.getOutputStream();
                    outputStream.write(params[0].getBytes());
                    outputStream.close();
                    inputStream = urlConnection.getInputStream();
                    byte[] buffer = new byte[128];
                    int a = inputStream.read(buffer);
                    res = new String(buffer,0,a);
                    inputStream.close();
                    return res;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if (inputStream != null)
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    if (outputStream != null)
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    if (urlConnection != null)
                            urlConnection.disconnect();

                }

                return null;
            }
        }.execute(token,userEmail);
    }
}
