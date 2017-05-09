package shark_tips.com.sharktips;


import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
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
public class UsersManager extends Fragment implements UserEditPanel.UserNameEditListener {

    private ListView listUsersAdminPanel;
    private EditText lblSearchUsers;
    private TextView lblShowResult;
    private UsersManagerAdapter adapter;
    private ArrayList<User> users;
    private String userEmail,userPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users_manager, container, false);
        lblSearchUsers = (EditText) view.findViewById(R.id.lblSearchUsers);
        listUsersAdminPanel = (ListView) view.findViewById(R.id.listUsersAdminPanel);
        lblShowResult = (TextView) view.findViewById(R.id.lblShowResult);
        userEmail = MyHelper.getUserEmailFromSharedPreferences(getContext());
        userPassword = MyHelper.getUserPasswordFromSharedPreferences(getContext());
        users = new ArrayList<>();
        adapter = new UsersManagerAdapter(getContext(),users);
        listUsersAdminPanel.setAdapter(adapter);

        new AsyncTask<String, Void, ArrayList<User>>() {
            @Override
            protected ArrayList<User> doInBackground(String... params) {
                HttpURLConnection urlConnection = null;
                InputStream inputStream = null;
                StringBuilder stringBuilder = new StringBuilder();
                JSONArray jsonArray = null;
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
                    jsonArray = new JSONArray(stringBuilder.toString());

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
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

                if (jsonArray != null){
                    for (int i = 0;i < jsonArray.length();i++){
                        try {
                            JSONObject userObject = jsonArray.getJSONObject(i);
                            User user = new User(userObject.getString("name")
                                    ,userObject.getString("lastName")
                                    ,userObject.getString("email")
                                    ,userObject.getLong("addTimeToUser")
                                    ,userObject.getBoolean("paid"));
                            users.add(user);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                return users;
            }

            @Override
            protected void onPostExecute(ArrayList<User> users) {
                adapter.notifyDataSetChanged();
            }
        }.execute(userEmail,userPassword);


        listUsersAdminPanel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager manager = getFragmentManager();
                UserEditPanel userEditPanel = new UserEditPanel();
                userEditPanel.setCancelable(true);
                userEditPanel.setUser(users.get(position));
                userEditPanel.setListener(UsersManager.this);
                userEditPanel.show(manager,"SHOW");
            }
        });

        return view;
    }

    @Override
    public void editUser() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showResult(String result) {
        lblShowResult.setVisibility(View.VISIBLE);
        lblShowResult.setTextColor(Color.parseColor("#042d44"));
        lblShowResult.setText("User has been:" + result);
    }
}
