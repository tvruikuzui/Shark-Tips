package shark_tips.com.sharktips;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,WebClickedListener{

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private PagerAdapter adapter;
    private String getUserEmail;
    private TextView lblSetUserEmail;
    private NavigationView navigationView;
    private BroadcastReceiver broadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(GcmRegisterIntentService.REGISTRATION_OK)){
                    String token = intent.getStringExtra("token");
                    Log.d("TOKEN",token);
                }else if (intent.getAction().equals(GcmRegisterIntentService.REGISTRATION_ERROR)){
                    Log.d("TOKEN","ERROR");
                }else {

                }
            }
        };

        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS != resultCode){
            if (apiAvailability.isUserResolvableError(resultCode)){
                Log.d("SERVER","Service not Installd /Enabled");
            }else {
               Log.d("SERVER","Device Not Soppurt google play servises");
            }
        }else {
            Intent intent = new Intent(this,GcmRegisterIntentService.class);
            startService(intent);
        }


        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addWindow(new MainHome(),"Home");
        adapter.addWindow(new Signals(),"Signals");
        Offers offers = new Offers();
        offers.setListener(this);
        adapter.addWindow(offers,"Offers");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        getUserEmail = MyHelper.getUserEmailFromSharedPreferences(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view  = navigationView.getHeaderView(0);
        lblSetUserEmail = (TextView)view.findViewById(R.id.lblSetUserEmail);
        lblSetUserEmail.setText(getUserEmail);

        checkIfUserAdmin();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("HOME","OnResume");
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,new IntentFilter(GcmRegisterIntentService.REGISTRATION_OK));
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,new IntentFilter(GcmRegisterIntentService.REGISTRATION_ERROR));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("HOME","OnPause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.nav_home:
                Intent intent = new Intent(this,Home.class);
                startActivity(intent);
                finish();
                break;

            case R.id.nav_logout:
                logOut();
                break;

            case R.id.nav_contact:
                sendEmail();
                break;

            case R.id.nav_share:
                shareToWhatsapp();
                break;

            case R.id.nav_admin:
                Intent adminIntent = new Intent(this,AdminPanel.class);
                startActivity(adminIntent);
                finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Share to Whatsapp
    private void shareToWhatsapp() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        startActivity(sendIntent);
        finish();
    }

    // Log out From the app
    private void logOut() {
        MyHelper.saveToSharedPreferences(this,false);
        MyHelper.saveUserEmailToSharedPreferences(this,null);
        MyHelper.saveUserPasswordToSharedPreferences(this,null);
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    // Send Mail To Admin
    protected void sendEmail() {
        String[] TO = {"Office@shark-tips.com"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void handleClick(boolean click) {

    }
    // Check if the user is an admin.
    private void checkIfUserAdmin() {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                HttpURLConnection urlConnection = null;
                InputStream inputStream = null;
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    URL url = new URL("http://35.184.144.226/shark2/"+ params[0] + "/" );
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setUseCaches(false);
                    urlConnection.connect();
                    inputStream = urlConnection.getInputStream();
                    byte [] buffer = new byte[256];
                    int actuallyRead;
                    while ((actuallyRead = inputStream.read(buffer)) != -1){
                            stringBuilder.append(new String(buffer,0,actuallyRead));
                    }
                    inputStream.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if (inputStream != null){
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (urlConnection != null){
                        urlConnection.disconnect();
                    }
                }

                try {
                    JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                    return jsonObject.getString("admin");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                Menu nav_Menu;
                switch (s){
                    case "SUPER_ADMIN":
                        nav_Menu = navigationView.getMenu();
                        nav_Menu.findItem(R.id.nav_admin).setVisible(true);
                        break;

                    case "SIGNAL_ADMIN":
                        nav_Menu = navigationView.getMenu();
                        nav_Menu.findItem(R.id.nav_admin).setVisible(true);
                        break;

                }
            }
        }.execute(getUserEmail);
    }
    
}
