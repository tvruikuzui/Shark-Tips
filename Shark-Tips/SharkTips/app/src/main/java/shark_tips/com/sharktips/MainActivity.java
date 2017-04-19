package shark_tips.com.sharktips;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements SendLogListener,LogInListener {

    private TabLayout tableLayout;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private boolean isUserLog;
    private SharedPreferences preferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the tool bar and The layout for the Tabs
        tableLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        SignupFragment signupFragment = new SignupFragment();
        signupFragment.setLogListener(this);
        pagerAdapter.addWindow(signupFragment,"SignUp");
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setListener(this);
        pagerAdapter.addWindow(loginFragment,"Login");
        viewPager.setAdapter(pagerAdapter);

        tableLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void onStart() {
        super.onStart();
            preferences = getSharedPreferences("data",MODE_PRIVATE);
            isUserLog = preferences.getBoolean("log",false);
            if (isUserLog == true){
                moveToHomeActivity();
            }
    }

    @Override
    public void sendLog(boolean isLogIn) {
        if (isLogIn == true){
            moveToHomeActivity();
        }
    }

    @Override
    public void logIn(boolean isLogIn) {
        if (isLogIn == true){
            moveToHomeActivity();
        }
    }

    public void moveToHomeActivity(){
        Intent intent = new Intent(this,Home.class);
        startActivity(intent);
        finish();
    }
}
