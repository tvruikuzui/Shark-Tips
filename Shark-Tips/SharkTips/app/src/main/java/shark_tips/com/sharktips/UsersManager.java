package shark_tips.com.sharktips;


import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class UsersManager extends Fragment implements UserEditPanel.UserNameEditListener{

    private FrameLayout framePaidUsers,frameUnPaidUsers;
    private ListView listPaidUsers,listUnPaidusers;
    private Button btnMoveToUnpaidUsers,btnMoveToPaidUsers;
    private  ArrayList<User> users;
    private  ArrayList<User> unPaidUsers;
    private UsersManagerAdapter adapterPaid,adapterUnpaid;
    private String userEmail,userPassword;
    private SearchView lblSearchPaidUsers,lblSearchUnPaidUsers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.paid_unpaid_users, container, false);

        userEmail = MyHelper.getUserEmailFromSharedPreferences(getContext());
        userPassword = MyHelper.getUserPasswordFromSharedPreferences(getContext());

        framePaidUsers = (FrameLayout) view.findViewById(R.id.framePaidUsers);
        frameUnPaidUsers = (FrameLayout) view.findViewById(R.id.frameUnPaidUsers);
        listPaidUsers = (ListView) view.findViewById(R.id.listPaidUsers);
        listUnPaidusers = (ListView) view.findViewById(R.id.listUnPaidUsers);
        listPaidUsers.setTextFilterEnabled(true);
        listUnPaidusers.setTextFilterEnabled(true);
        users = new ArrayList<>();
        unPaidUsers = new ArrayList<>();
        adapterPaid = new UsersManagerAdapter(getContext(),users);
        listPaidUsers.setAdapter(adapterPaid);
        adapterUnpaid = new UsersManagerAdapter(getContext(),unPaidUsers);
        listUnPaidusers.setAdapter(adapterUnpaid);


        startSynck();

        lblSearchPaidUsers = (SearchView) view.findViewById(R.id.lblSearchPaidUsers);
        lblSearchPaidUsers.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapterPaid.getFilter().filter(newText);
                adapterPaid.notifyDataSetChanged();
                return true;
            }
        });
        lblSearchUnPaidUsers = (SearchView) view.findViewById(R.id.lblSearchUnpaidUsers);
        lblSearchUnPaidUsers.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterUnpaid.getFilter().filter(newText);
                adapterUnpaid.notifyDataSetChanged();
                return false;
            }
        });

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

        listPaidUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                FragmentManager manager = getFragmentManager();
                UserEditPanel userEditPanel = new UserEditPanel();
                userEditPanel.setCancelable(true);
                userEditPanel.setUser((User) adapterPaid.getItem(position));
                userEditPanel.setListener(UsersManager.this);
                userEditPanel.show(manager,"SHOW");

            }
        });

        listUnPaidusers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager manager = getFragmentManager();
                UserEditPanel userEditPanel = new UserEditPanel();
                userEditPanel.setCancelable(true);
                userEditPanel.setUser((User) adapterUnpaid.getItem(position));
                userEditPanel.setListener(UsersManager.this);
                userEditPanel.show(manager,"SHOW");

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
                                    ,0
                                    ,userObject.getBoolean("paid"));
                            long time = (userObject.getLong("ts") - System.currentTimeMillis())/86400000L;
                            user.setTimeStamp(time + userObject.getInt("addTimeToUser"));

                            if (user.isPaid()){
                                users.add(user);
                            }else {
                                unPaidUsers.add(user);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.execute(userEmail,userPassword);
    }

    @Override
    public void editUser() {

    }

    @Override
    public void showResult(String result) {
        Toast.makeText(getContext(), "user has been " + result, Toast.LENGTH_SHORT).show();
    }
}
