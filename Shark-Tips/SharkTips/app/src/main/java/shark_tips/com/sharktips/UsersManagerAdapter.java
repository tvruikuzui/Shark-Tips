package shark_tips.com.sharktips;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liranelyadumi on 4/27/17.
 */

public class UsersManagerAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<User> users;


    public UsersManagerAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class UserRow{
        TextView lblUserNameAdminPanel
                ,lblUserLastAdminPanel
                ,lblUserEmailAdminPanel
                ,lblUserDaysAdminPanel;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserRow userRow;
        if (convertView == null) {
            userRow = new UserRow();
            convertView = LayoutInflater.from(context).inflate(R.layout.users_row_admin, parent, false);
            userRow.lblUserNameAdminPanel = (TextView) convertView.findViewById(R.id.lblUserNameAdminPanel);
            userRow.lblUserLastAdminPanel = (TextView) convertView.findViewById(R.id.lblUserLastAdminPanel);
            userRow.lblUserEmailAdminPanel = (TextView) convertView.findViewById(R.id.lblUserEmailAdminPanel);
            userRow.lblUserDaysAdminPanel = (TextView) convertView.findViewById(R.id.lblUserDaysAdminPanel);

            convertView.setTag(userRow);
        }else {
            userRow = (UserRow) convertView.getTag();
        }

        User user = users.get(position);
        userRow.lblUserNameAdminPanel.setText(user.getName());
        userRow.lblUserLastAdminPanel.setText(user.getLastName());
        userRow.lblUserEmailAdminPanel.setText(user.getMail());
        userRow.lblUserDaysAdminPanel.setText(String.valueOf(user.getTimeStamp()));

        return convertView;
    }


}
