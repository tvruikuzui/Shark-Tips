package shark_tips.com.sharktips;


import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 */
public class UsersManager extends Fragment{



    /*
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private PagerAdapter pagerAdapter;
*/


    /*
        // Create the tool bar and The layout for the Tabs
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        pagerAdapter = new PagerAdapter(getFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        UserPaid userPaid = new UserPaid();
        pagerAdapter.addWindow(userPaid, "Paid Users");
        Unpaidusers unpaidusers = new Unpaidusers();
        pagerAdapter.addWindow(unpaidusers, "Unpaid Users");
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
*/

    private FrameLayout framePaidUsers,frameUnPaidUsers;
    private ListView listPaidUsers,listUnPaidusers;
    private Button btnMoveToUnpaidUsers,btnMoveToPaidUsers;
    private  ArrayList<User> users;
    private  ArrayList<User> unPaidUsers;
    private UsersManagerAdapter adapter;
    private String userEmail,userPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.paid_unpaid_users, container, false);
        userEmail = MyHelper.getUserEmailFromSharedPreferences(getContext());
        userPassword = MyHelper.getUserPasswordFromSharedPreferences(getContext());
        startSynck();
        framePaidUsers = (FrameLayout) view.findViewById(R.id.framePaidUsers);
        frameUnPaidUsers = (FrameLayout) view.findViewById(R.id.frameUnPaidUsers);
        listPaidUsers = (ListView) view.findViewById(R.id.listPaidUsers);
        listUnPaidusers = (ListView) view.findViewById(R.id.listUnPaidUsers);
        users = new ArrayList<>();
        unPaidUsers = new ArrayList<>();
        adapter = new UsersManagerAdapter(getContext(),unPaidUsers);
        listUnPaidusers.setAdapter(adapter);
        btnMoveToUnpaidUsers = (Button) view.findViewById(R.id.btnMoveToUnpaidUsers);
        btnMoveToUnpaidUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                framePaidUsers.setVisibility(View.GONE);
                frameUnPaidUsers.setVisibility(View.VISIBLE);
            }
        });


        btnMoveToPaidUsers = (Button) view.findViewById(R.id.btnMoveToPaidUsers);
        btnMoveToPaidUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameUnPaidUsers.setVisibility(View.GONE);
                framePaidUsers.setVisibility(View.VISIBLE);
            }
        });

        return view;

    }










    private void startSynck(){
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                HttpURLConnection urlConnection = null;
                InputStream inputStream = null;
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    URL url = new URL("http://35.184.144.226/shark2/admin/"+params[0]+"/"+params[1]+"/");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    urlConnection.setUseCaches(false);
                    urlConnection.connect();
                    inputStream = urlConnection.getInputStream();
                    byte [] buffer = new byte[512];
                    int actuallyRead;
                    while ((actuallyRead = inputStream.read(buffer)) != -1){
                        stringBuilder.append(new String(buffer,0,actuallyRead));
                    }
                    inputStream.close();
                    urlConnection.disconnect();
                    return stringBuilder.toString();

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


                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (jsonArray != null){
                    for (int i = 0;i < jsonArray.length();i++){
                        try {
                            JSONObject userObject = jsonArray.getJSONObject(i);
                            User user = new User(userObject.getString("name")
                                    ,userObject.getString("lastName")
                                    ,userObject.getString("email")
                                    ,userObject.getLong("addTimeToUser")
                                    ,userObject.getBoolean("paid"));
                            if (user.isPaid())
                                users.add(user);
                            else unPaidUsers.add(user);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.execute(userEmail,userPassword);
    }

}
