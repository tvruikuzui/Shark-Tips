package shark_tips.com.sharktips;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Home extends AppCompatActivity {


    private SharedPreferences preferences;
    private boolean checkUserLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        preferences = getSharedPreferences("data",MODE_PRIVATE);
        checkUserLog = preferences.getBoolean("log",false);
        if (checkUserLog == false){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }


    }
}
