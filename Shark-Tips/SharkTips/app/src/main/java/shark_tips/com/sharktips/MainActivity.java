package shark_tips.com.sharktips;


import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;


public class MainActivity extends AppCompatActivity implements LogInListener,SignUpListener {

    private TabLayout tableLayout;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private boolean isLogin = false;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "test");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "test");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        // Create the tool bar and The layout for the Tabs
        tableLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        SignupFragment signupFragment = new SignupFragment();
        signupFragment.setListener(this);
        pagerAdapter.addWindow(signupFragment,"SIGN UP");
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setListener(this);
        pagerAdapter.addWindow(loginFragment,"LOGIN");
        viewPager.setAdapter(pagerAdapter);

        tableLayout.setupWithViewPager(viewPager);

    }


    @Override
    protected void onStart() {
        super.onStart();
        isLogin = MyHelper.getDataFromSharedPreferences(this);
        if (getIntent().hasExtra("click_action") && isLogin || getIntent().hasExtra("go") && isLogin){
            Intent intent = new Intent(this,Notification.class);
            startActivity(intent);
            finish();

        }
        if (isLogin){
               moveToHomeActivity();
        }
    }


    @Override
    public void checkUserLogFromLogin(boolean log) {
        if (log == true){
            moveToHomeActivity();
            return;
        }
    }

    @Override
    public void checkUserLogFromSignUp(boolean log) {
        if (log == true){
            moveToHomeActivity();
            return;
        }
    }

    private void moveToHomeActivity(){
        Intent intent = new Intent(this,Home.class);
        startActivity(intent);
        finish();
    }

}
