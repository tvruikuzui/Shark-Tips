package shark_tips.com.sharktips;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AdminPanel extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        // Create the tool bar and The layout for the Tabs
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addWindow(new SignalsManager(),"Signals Manager");
        adapter.addWindow(new UsersManager(),"Users Manager");
        adapter.addWindow(new Messages(),"Messages");
        adapter.addWindow(new Performance(),"Performance");
        adapter.addWindow(new AdminPicker(),"Admin Picker");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
