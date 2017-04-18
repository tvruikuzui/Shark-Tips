package shark_tips.com.sharktips;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Signals extends Fragment {

    private ListView listView;
    private ExpandListAdapter adapter;
    private List<Signal> signals;
    private Signal signal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signals, container, false);
        listView = (ListView) view.findViewById(R.id.listSignals);
        signals = new ArrayList<>();
        adapter = new ExpandListAdapter(getContext(),signals);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                signal = (Signal) adapter.getItem(position);
                if (signal != null){

                    if (signal.isExpanded){
                        signal.isExpanded = false;
                    }else {
                        signal.isExpanded = true;
                    }
                }
                adapter.notifyDataSetChanged();
            }

        });
        return view;
    }

}
