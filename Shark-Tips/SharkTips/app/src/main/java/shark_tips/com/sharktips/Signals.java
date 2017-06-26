package shark_tips.com.sharktips;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Signals extends Fragment implements EditSignalsAdmin.UpdateSignalAlertListener {

    private ListView listView;
    private ExpandListAdapter adapter;
    private List<Signal> signals;
    private Signal signal;
    private static final String BASE_URL = "http://35.184.144.226/shark2/signals/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signals, container, false);
        listView = (ListView) view.findViewById(R.id.listSignals);
        signals = new ArrayList<>();
        adapter = new ExpandListAdapter(getContext(),signals);
        listView.setAdapter(adapter);

        if (Home.isAdmin){
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    FragmentManager manager = getFragmentManager();
                    EditSignalsAdmin signalsAdmin = new EditSignalsAdmin();
                    signalsAdmin.setListener(Signals.this);
                    signalsAdmin.setSignal(signals.get(position));
                    signalsAdmin.show(manager,"TAG");
                    return false;
                }
            });
        }

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                HttpURLConnection urlConnection = null;
                InputStream inputStream = null;
                JSONArray jsonArray = null;
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    URL url = new URL(BASE_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setUseCaches(false);
                    urlConnection.connect();
                    inputStream = urlConnection.getInputStream();
                    int actuallyRead;
                    byte [] buffer = new byte[256];
                    while ((actuallyRead = inputStream.read(buffer))!= -1){
                        stringBuilder.append(new String(buffer,0,actuallyRead));
                    }
                    inputStream.close();
                    jsonArray = new JSONArray(stringBuilder.toString());
                    if (jsonArray != null){
                        List<Signal> close = new ArrayList<Signal>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject signalObject = jsonArray.getJSONObject(i);
                            signal = new Signal
                                    (signalObject.getBoolean("open")
                                            ,signalObject.getInt("time")
                                            ,signalObject.getInt("id")
                                            ,signalObject.getString("currency")
                                            ,signalObject.getBoolean("buy")
                                            ,signalObject.getDouble("price")
                                            ,signalObject.getDouble("sellStop")
                                            ,signalObject.getDouble("tp1")
                                            ,signalObject.getDouble("tp2")
                                            ,signalObject.getString("not")
                                            ,signalObject.getString("nameOfSl"));
                            signal.setTs(signalObject.getLong("ts"));
                            if (signal.isOpen())
                            wereToAddSignals(signal,signals);
                            else
                                wereToAddSignals(signal,close);
                        }
                        Collections.sort(signals);
                        Collections.sort(close);
                        signals.addAll(close);
                    }
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
                        if (urlConnection != null){
                            urlConnection.disconnect();
                        }
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                adapter.notifyDataSetChanged();
            }
        }.execute();

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

    //new method
    private void wereToAddSignals(Signal signal,List<Signal>list) {
        int hoursPassForSignals = (int) (System.currentTimeMillis() - signal.getTs()) / 3600000;
        if (hoursPassForSignals > 24 && hoursPassForSignals < 48){
            signal.setTime(hoursPassForSignals / 24);
            signal.setHuersDays("day");
            list.add(0,signal);
            return;
        }
        if (hoursPassForSignals > 48){
            signal.setTime(hoursPassForSignals / 24);
            signal.setHuersDays("days");
            list.add(0,signal);
        }else {
            if (hoursPassForSignals > 1) {
                signal.setTime(hoursPassForSignals);
                signal.setHuersDays("hrs");
                list.add(0,signal);
            }
            else {
                signal.setTime(hoursPassForSignals);
                signal.setHuersDays("hr");
                list.add(0,signal);
            }
        }
    }

    @Override
    public void signalUpdate() {
        adapter.notifyDataSetChanged();
    }
}
