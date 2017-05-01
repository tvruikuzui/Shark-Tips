package shark_tips.com.sharktips;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

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

            registerComplete = new Intent(REGISTRATION_OK);
            registerComplete.putExtra("token",token);

        }catch (Exception e){
            registerComplete = new Intent(REGISTRATION_ERROR);
            Log.d("GCM","ERROR");
        }

        LocalBroadcastManager.getInstance(this).sendBroadcast(registerComplete);
    }
}
