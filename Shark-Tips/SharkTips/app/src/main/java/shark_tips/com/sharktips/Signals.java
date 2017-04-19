package shark_tips.com.sharktips;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
    private static final String BASE_URL = "http://35.184.144.226/shark1/signals/";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signals, container, false);
        listView = (ListView) view.findViewById(R.id.listSignals);
        signals = new ArrayList<>();
        adapter = new ExpandListAdapter(getContext(),signals);
        listView.setAdapter(adapter);

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
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject signalObject = jsonArray.getJSONObject(i);
                            signal = new Signal(signalObject.getBoolean("open")
                                    ,signalObject.getInt("time")
                                    ,signalObject.getString("currency")
                                    ,signalObject.getBoolean("buy")
                                    ,signalObject.getDouble("price")
                                    ,signalObject.getDouble("sellStop")
                                    ,signalObject.getDouble("sl")
                                    ,signalObject.getDouble("tp1"),signalObject.getDouble("tp2")
                                    ,signalObject.getString("note"));
                            signals.add(signal);
                        }
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

}
