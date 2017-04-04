package shark_tips.com.sharktips;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TelephonyManager manager = (TelephonyManager) this.getSystemService(this.TELEPHONY_SERVICE);
        String id = manager.getDeviceId();

        // DO YOU SEE THIS ?

    }
}
