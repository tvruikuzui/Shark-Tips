package shark_tips.com.sharktips;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by liranelyadumi on 6/5/17.
 */

public class AdminsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<User> users;



    public AdminsAdapter (Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
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

    public class AdminRow{
        TextView lblShowAdmins,lblAdminsType;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdminRow adminRow;
        if (convertView == null) {
            adminRow = new AdminRow();
            convertView = LayoutInflater.from(context).inflate(R.layout.admins_row, parent, false);
            adminRow.lblShowAdmins = (TextView) convertView.findViewById(R.id.lblShowAdmins);
            adminRow.lblAdminsType = (TextView) convertView.findViewById(R.id.lblAdminType);
            convertView.setTag(adminRow);
        }else {
            adminRow = (AdminRow) convertView.getTag();
        }

        User user = users.get(position);
        adminRow.lblShowAdmins.setText(user.getMail());
        if (user.getAdminType().equals("SUPER_ADMIN")){
            adminRow.lblAdminsType.setTextColor(Color.parseColor("#aa0036"));
        }else {
            adminRow.lblAdminsType.setTextColor(Color.parseColor("#8bba00"));
        }
        adminRow.lblAdminsType.setText(user.getAdminType());

            return convertView;

    }
}
