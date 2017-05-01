package shark_tips.com.sharktips;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by liranelyadumi on 5/1/17.
 */

public class GcmTokenRefreshListener extends InstanceIDListenerService {


    // when token refresh start service

    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this,GcmRegisterIntentService.class);
        startService(intent);
    }
}
