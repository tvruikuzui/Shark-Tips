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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.paid_unpaid_users, container, false);
        framePaidUsers = (FrameLayout) view.findViewById(R.id.framePaidUsers);
        frameUnPaidUsers = (FrameLayout) view.findViewById(R.id.frameUnPaidUsers);
        listPaidUsers = (ListView) view.findViewById(R.id.listPaidUsers);
        listUnPaidusers = (ListView) view.findViewById(R.id.listUnPaidUsers);

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

}
