package shark_tips.com.sharktips;


import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
public class UserPaid extends Fragment implements UserEditPanel.UserNameEditListener,CheckPaidUsers.GetUsersListListener{

    private CheckPaidUsers checker;
    private ListView listUsersAdminPanel;
    private TextView lblShowResult;
    private UsersManagerAdapter adapter;
    private ArrayList<User> users;
    private SearchView lblSearchUsers;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        users = new ArrayList<>();
        checker = new CheckPaidUsers(MyHelper.getUserEmailFromSharedPreferences(getContext()),MyHelper.getUserPasswordFromSharedPreferences(getContext()));
        checker.setListener(this);
        checker.asyncUsers();
        View view = inflater.inflate(R.layout.fragment_user_paid, container, false);

        listUsersAdminPanel = (ListView) view.findViewById(R.id.listUsersAdminPanel);
        lblShowResult = (TextView) view.findViewById(R.id.lblShowResult);
        lblSearchUsers = (SearchView) view.findViewById(R.id.lblSearchUsers);

        listUsersAdminPanel.setTextFilterEnabled(true);
        lblSearchUsers.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        listUsersAdminPanel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager manager = getFragmentManager();
                UserEditPanel userEditPanel = new UserEditPanel();
                userEditPanel.setCancelable(true);
                userEditPanel.setUser((User) adapter.getItem(position));
                userEditPanel.setListener(UserPaid.this);
                userEditPanel.show(manager,"SHOW");
            }
        });

        adapter = new UsersManagerAdapter(getContext(),users);
        listUsersAdminPanel.setAdapter(adapter);

        return view;
    }

    @Override
    public void editUser() {
        checker.asyncUsers();
    }

    @Override
    public void showResult(String result) {
        lblShowResult.setVisibility(View.VISIBLE);
        lblShowResult.setTextColor(Color.parseColor("#042d44"));
        lblShowResult.setText("User has been: " + result);
    }

    @Override
    public void getPaidUsers(ArrayList<User> users) {
        this.users = users;
        adapter = new UsersManagerAdapter(getContext(),users);
        listUsersAdminPanel.setAdapter(adapter);
    }

    @Override
    public void getUnPaidUsers(ArrayList<User> users) {

    }
}
