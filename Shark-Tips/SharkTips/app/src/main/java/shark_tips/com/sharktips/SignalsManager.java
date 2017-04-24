package shark_tips.com.sharktips;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignalsManager extends Fragment {

    private Spinner spnAction;
    private EditText txtCurrency,txtPrice,txtSellStop,txtSl1,txtTp1,txtTp2,txtNote;
    private ArrayAdapter<CharSequence> actionAdapter;
    private Signal signal;
    private Button btnSendSignal;
    private int position;

    public void setSignal(Signal signal) {
        this.signal = signal;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signals_manager, container, false);
        spnAction = (Spinner) view.findViewById(R.id.spnAction);
        actionAdapter = ArrayAdapter.createFromResource(getContext(),R.array.action,android.R.layout.simple_spinner_item);
        actionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAction.setAdapter(actionAdapter);

        txtCurrency = (EditText) view.findViewById(R.id.txtCurrency);
        txtPrice = (EditText) view.findViewById(R.id.txtPrice);
        txtSellStop = (EditText) view.findViewById(R.id.txtSellStop);
        txtSl1 = (EditText) view.findViewById(R.id.txtSl);
        txtTp1 = (EditText) view.findViewById(R.id.txtTp1);
        txtTp2 = (EditText) view.findViewById(R.id.txtTp2);
        txtNote = (EditText) view.findViewById(R.id.txtNote);

        btnSendSignal = (Button) view.findViewById(R.id.btnSendSignal);
        btnSendSignal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signal = new Signal();
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        HttpURLConnection urlConnection = null;
                        OutputStream outputStream = null;
                        try {
                            URL url = new URL("");
                            urlConnection = (HttpURLConnection) url.openConnection();
                            urlConnection.setRequestMethod("GET");
                            urlConnection.setUseCaches(false);
                            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                            urlConnection.connect();
                            outputStream = urlConnection.getOutputStream();
                            JSONObject signalObject = new JSONObject();
                            signalObject.put("",signal.getCurrency());
                            signalObject.put("",signal.getPrice());
                            signalObject.put("",signal.getSellStop());
                            signalObject.put("",signal.getSl());
                            signalObject.put("",signal.getTp1());
                            signalObject.put("",signal.getTp2());
                            signalObject.put("",signal.getNote());
                            outputStream.write(signalObject.toString().getBytes());
                            outputStream.close();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }finally {
                            if (outputStream != null){
                                try {
                                    outputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (urlConnection != null){
                                urlConnection.disconnect();
                            }
                        }
                        return null;
                    }
                }.execute();
            }
        });



        return view;
    }

}
