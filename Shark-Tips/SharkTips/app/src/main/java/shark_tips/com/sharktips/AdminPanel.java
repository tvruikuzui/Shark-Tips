package shark_tips.com.sharktips;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;


public class AdminPanel extends AppCompatActivity {


    private TextView lblChooseAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        lblChooseAction = (TextView) findViewById(R.id.lblChooseAction);

    }

    public void openUsers(View view) {
        UsersManager usersManager = new UsersManager();
        createFragment(usersManager);
    }

    public void openSignals(View view) {
         SignalsManager signalsManager = new SignalsManager();
         createFragment(signalsManager);
    }

    public void openMsg(View view) {
        Messages messages = new Messages();
        createFragment(messages);

    }

    public void openTreds(View view) {
        Performance performance = new Performance();
        createFragment(performance);
    }

    public void openAdminPicker(View view) {
        AdminPicker adminPicker = new AdminPicker();
        createFragment(adminPicker);
    }

    // Create Fragment
    private void createFragment(Fragment fragment){
        lblChooseAction.setVisibility(View.GONE);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.adminFrame,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
