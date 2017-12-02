package shark_tips.com.sharktips;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;


public class AdminPanel extends AppCompatActivity {


    private TextView lblChooseAction;
    private UsersManager usersManager;
    private SignalsManager signalsManager;
    private Messages messages;
    private Performance performance;
    private AdminPicker adminPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        lblChooseAction = (TextView) findViewById(R.id.lblChooseAction);
        usersManager = new UsersManager();
        signalsManager = new SignalsManager();
        messages = new Messages();
        performance = new Performance();
        adminPicker = new AdminPicker();


    }

    public void openUsers(View view) {

        createFragment(usersManager);
    }

    public void openSignals(View view) {

         createFragment(signalsManager);
    }

    public void openMsg(View view) {

        createFragment(messages);

    }

    public void openTreds(View view) {

        createFragment(performance);
    }

    public void openAdminPicker(View view) {

        createFragment(adminPicker);
    }

    // Create Fragment
    private  void createFragment(Fragment fragment){
        lblChooseAction.setVisibility(View.GONE);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.adminFrame,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void backToMain(View view) {
        Intent intent = new Intent(this,Home.class);
        startActivity(intent);
        finish();
    }
}
