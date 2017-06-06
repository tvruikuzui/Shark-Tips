package shark_tips.com.sharktips;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import java.util.Collections;
import java.util.List;

public class Notification extends AppCompatActivity {

    private ListView listView;
    private ExpandListAdapter adapter;
    private List<Signal> signals;
    private Signal signal;
    private static final String BASE_URL = "http://35.184.144.226/shark2/signals/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        listView = (ListView)findViewById(R.id.listOPenSignals);
        signals = new ArrayList<>();
        adapter = new ExpandListAdapter(this,signals);
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
                            signal = new Signal
                                    (signalObject.getBoolean("open")
                                            ,signalObject.getInt("time")
                                            ,signalObject.getInt("id")
                                            ,signalObject.getString("currency")
                                            ,signalObject.getBoolean("buy")
                                            ,signalObject.getDouble("price")
                                            ,signalObject.getDouble("sellStop")
                                            ,signalObject.getDouble("sl")
                                            ,signalObject.getDouble("tp1")
                                            ,signalObject.getDouble("tp2")
                                            ,signalObject.getString("not")
                                            ,signalObject.getString("nameOfSl"));
                            signal.setTs(signalObject.getLong("ts"));
                            wereToAddSignals(signal);
                            signal.isExpanded = true;
                        }
                        Collections.sort(signals);
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
    }

    //new method
    private void wereToAddSignals(Signal signal) {
        int hoursPassForSignals = (int) (System.currentTimeMillis() - signal.getTs()) / 3600000;
        if (hoursPassForSignals > 24 && hoursPassForSignals < 48){
            signal.setTime(hoursPassForSignals / 24);
            signal.setHuersDays("day");
            signals.add(signal);
            return;
        }
        if (hoursPassForSignals > 48){
            signal.setTime(hoursPassForSignals / 24);
            signal.setHuersDays("days");
            signals.add(signal);
        }else {
            if (hoursPassForSignals > 1) {
                signal.setTime(hoursPassForSignals);
                signal.setHuersDays("hrs");
                signals.add(signal);
            }
            else {
                signal.setTime(hoursPassForSignals);
                signal.setHuersDays("hr");
                signals.add(signal);
            }
        }
    }

    public void backToMainImg(View view) {
        Intent intent = new Intent(this,Home.class);
        startActivity(intent);
        finish();
    }
}
