package shark_tips.com.sharktips;



import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Unpaidusers extends Fragment implements CheckPaidUsers.GetUsersListListener,UserEditPanel.UserNameEditListener{

    private CheckPaidUsers checker;
    ArrayList<User> unpaidUsers;
    private ListView listUsersUnpaid;
    private UsersManagerAdapter adapter;
    private SearchView lblSearchUsers;
    private TextView lblShowResult;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        unpaidUsers = new ArrayList<>();
        checker = new CheckPaidUsers(MyHelper.getUserEmailFromSharedPreferences(getContext()),MyHelper.getUserPasswordFromSharedPreferences(getContext()));
        checker.setListener(this);
        checker.asyncUsers();
        View view =  inflater.inflate(R.layout.fragment_unpaidusers, container, false);

        listUsersUnpaid = (ListView) view.findViewById(R.id.listUsersUnpaid);
        lblShowResult = (TextView) view.findViewById(R.id.lblShowResult);
        lblSearchUsers = (SearchView) view.findViewById(R.id.lblSearchUsers);
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

        listUsersUnpaid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                FragmentManager manager = getFragmentManager();
                UserEditPanel userEditPanel = new UserEditPanel();
                userEditPanel.setCancelable(true);
                userEditPanel.setUser((User) adapter.getItem(position));
                userEditPanel.setListener(Unpaidusers.this);
                userEditPanel.show(manager,"SHOW");
            }


        });

        adapter = new UsersManagerAdapter(getContext(),unpaidUsers);
        listUsersUnpaid.setAdapter(adapter);

        return view;
    }

    @Override
    public void getPaidUsers(ArrayList<User> users) {

    }

    @Override
    public void getUnPaidUsers(ArrayList<User> users) {
        unpaidUsers = users;
        adapter = new UsersManagerAdapter(getContext(),unpaidUsers);
        listUsersUnpaid.setAdapter(adapter);
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
}
