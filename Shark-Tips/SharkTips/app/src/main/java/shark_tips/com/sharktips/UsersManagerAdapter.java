package shark_tips.com.sharktips;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by liranelyadumi on 4/27/17.
 */

public class UsersManagerAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private ArrayList<User> users;
    private ArrayList<User> filterList;
    private CustomFilter filter;


    public UsersManagerAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
        this.filterList = users;
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
        int itemID;
        if (users == null){
            itemID = position;
        }else {
            itemID = users.indexOf(filterList.get(position));
        }
        return itemID;
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new CustomFilter();
        }
        return filter;
    }

    public class CustomFilter extends Filter {


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0){
                constraint = constraint.toString().toUpperCase();
                ArrayList<User> filters = new ArrayList<User>();
                for (int i = 0; i < filterList.size(); i++) {
                    if (filterList.get(i).getMail().toUpperCase().contains(constraint)){
                        User user = new User(filterList.get(i).getMail());
                        filters.add(user);
                    }
                }

                results.count = filters.size();
                results.values = filters;
            }else {
                results.count = filterList.size();
                results.values = filterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            users = (ArrayList<User>) results.values;
            notifyDataSetChanged();
        }
    }

    public class UserRow{
        TextView lblUserNameAdminPanel
                ,lblUserLastAdminPanel
                ,lblUserEmailAdminPanel
                ,lblUserDaysAdminPanel;
        ImageView imgPaid;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserRow userRow;
        if (convertView == null) {
            userRow = new UserRow();
            convertView = LayoutInflater.from(context).inflate(R.layout.users_row_admin, parent, false);
            userRow.imgPaid = (ImageView) convertView.findViewById(R.id.imgPaid);
            userRow.lblUserNameAdminPanel = (TextView) convertView.findViewById(R.id.lblUserNameAdminPanel);
            userRow.lblUserLastAdminPanel = (TextView) convertView.findViewById(R.id.lblUserLastAdminPanel);
            userRow.lblUserEmailAdminPanel = (TextView) convertView.findViewById(R.id.lblUserEmailAdminPanel);
            userRow.lblUserDaysAdminPanel = (TextView) convertView.findViewById(R.id.lblUserDaysAdminPanel);

            convertView.setTag(userRow);
        }else {
            userRow = (UserRow) convertView.getTag();
        }

        User user = users.get(position);
        if (user.isPaid() == false){
            userRow.imgPaid.setImageResource(R.drawable.payoff);
        }else {
            userRow.imgPaid.setImageResource(R.drawable.payon);
        }
        userRow.lblUserNameAdminPanel.setText(user.getName());
        userRow.lblUserLastAdminPanel.setText(user.getLastName());
        userRow.lblUserEmailAdminPanel.setText(user.getMail());
        if (user.getTimeStamp() <=  1){
            userRow.lblUserDaysAdminPanel.setText(String.valueOf(user.getTimeStamp() + " day"));
        }else {
            userRow.lblUserDaysAdminPanel.setText(String.valueOf(user.getTimeStamp() + " days"));
        }


        return convertView;
    }


}
