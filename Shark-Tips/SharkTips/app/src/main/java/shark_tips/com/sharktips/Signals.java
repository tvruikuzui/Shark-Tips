package shark_tips.com.sharktips;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
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
    private Button btnAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signals, container, false);
        listView = (ListView) view.findViewById(R.id.listSignals);
        btnAdd = (Button) view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signal signal = new Signal();
                signal.setStatus("open");
                signal.setAction("buy");
                signal.setCurrency("GBPCAD");
                signal.setTime("12:00");
                signal.setPrice("300");
                signal.setSellStop("1.6610");
                signal.setSl("1.6650");
                signal.setTp1("1.6590");
                signal.setTp2("1.7580");
                signals.add(signal);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
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
