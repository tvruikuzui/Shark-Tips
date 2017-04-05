package shark_tips.com.sharktips;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements AddUserListener {


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
        pagerAdapter.addWindow(new LoginFragment(),"Login");

        SignupFragment signupFragment = new SignupFragment();
        signupFragment.setListener(this);
        pagerAdapter.addWindow(signupFragment,"SignUp");
        viewPager.setAdapter(pagerAdapter);

        tableLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void addUser(User user) {
        Log.d("USER",user.toString());
    }
}
