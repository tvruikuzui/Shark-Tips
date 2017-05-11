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
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignalsManager extends Fragment {

    private Spinner spnAction;
    private EditText txtCurrency,txtPrice,txtSellStop,txtSl,txtTp1,txtTp2,txtNote;
    private ArrayAdapter<CharSequence> actionAdapter;
    private Signal signal;
    private Button btnSendSignal;
    private String setCurrency,setPrice,setSellStop,setSl,setTp1,setTp2,setNote,userPassword,userEmail;

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

        userEmail = MyHelper.getUserEmailFromSharedPreferences(getContext());
        userPassword = MyHelper.getUserPasswordFromSharedPreferences(getContext());

        txtCurrency = (EditText) view.findViewById(R.id.txtCurrency);
        txtPrice = (EditText) view.findViewById(R.id.txtPrice);
        txtSellStop = (EditText) view.findViewById(R.id.txtSellStop);
        txtSl = (EditText) view.findViewById(R.id.txtSl);
        txtTp1 = (EditText) view.findViewById(R.id.txtTp1);
        txtTp2 = (EditText) view.findViewById(R.id.txtTp2);
        txtNote = (EditText) view.findViewById(R.id.txtNote);


        btnSendSignal = (Button) view.findViewById(R.id.btnSendSignal);
        btnSendSignal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewSignal();
                new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        params[0] = userEmail;
                        params[1] = userPassword;
                        HttpURLConnection urlConnection = null;
                        OutputStream outputStream = null;
                        InputStream inputStream = null;
                        String result = "";
                        try {
                            URL url = new URL("http://35.184.144.226/shark2/admin/"+params[0]+"/"+params[1]);
                            urlConnection = (HttpURLConnection) url.openConnection();
                            urlConnection.setRequestMethod("PUT");
                            urlConnection.setUseCaches(false);
                            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                            urlConnection.connect();
                            outputStream = urlConnection.getOutputStream();
                            JSONObject signalObject = new JSONObject();
                            signalObject.put("currency",signal.getCurrency());
                            signalObject.put("price",signal.getPrice());
                            signalObject.put("sellStop",signal.getSellStop());
                            signalObject.put("sl",signal.getSl());
                            signalObject.put("tp1",signal.getTp1());
                            signalObject.put("tp2",signal.getTp2());
                            signalObject.put("note",signal.getNote());
                            outputStream.write(signalObject.toString().getBytes());
                            outputStream.close();
                            inputStream = urlConnection.getInputStream();
                            byte[] buffer = new byte[64];
                            int actuallyRead = inputStream.read(buffer);
                            result = new String(buffer,0,actuallyRead);
                            inputStream.close();
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
                        return result;
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        switch (result){
                            case "ok":
                                Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
                                break;
                            case "error":
                                Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
                        }
                    }
                }.execute();
                SignalsAsyncTask signalsAsyncTask = new SignalsAsyncTask();
                signalsAsyncTask.setC(getContext());
                signalsAsyncTask.execute(signal);
            }
        });



        return view;
    }


    private void createNewSignal(){
        setCurrency = txtCurrency.getText().toString();
        setPrice = txtPrice.getText().toString();
        setSellStop = txtSellStop.getText().toString();
        setSl = txtSl.getText().toString();
        setTp1 = txtTp1.getText().toString();
        setTp2 = txtTp2.getText().toString();
        setNote = txtNote.getText().toString();
        signal = new Signal();
        signal.setCurrency(setCurrency);
        signal.setPrice(Double.parseDouble(setPrice));
        signal.setSellStop(Double.parseDouble(setSellStop));
        signal.setSl(Double.parseDouble(setSl));
        signal.setTp1(Double.parseDouble(setTp1));
        signal.setTp2(Double.parseDouble(setTp2));
        signal.setNote(setNote);
    }

}
