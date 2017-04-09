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

public class MainActivity extends AppCompatActivity implements SendLogListener {


    private Toolbar toolbar;
    private TabLayout tableLayout;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Create the tool bar and The layout for the Tabs
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        tableLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        SignupFragment signupFragment = new SignupFragment();
        signupFragment.setLogListener(this);
        pagerAdapter.addWindow(signupFragment,"SignUp");
        pagerAdapter.addWindow(new LoginFragment(),"Login");
        viewPager.setAdapter(pagerAdapter);

        tableLayout.setupWithViewPager(viewPager);


    }

    @Override
    public void sendLog(boolean isLogIn) {
        if (isLogIn == true){
            Intent intent = new Intent(this,Home.class);
            startActivity(intent);
            finish();
        }
    }
}
