package shark_tips.com.sharktips;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class Performance extends Fragment {

    private  EditText txtPerformance;
    private Button btnUpdate;
    private  String performanceUpdate,userEmail,userPassword;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_performance, container, false);

        userEmail = MyHelper.getUserEmailFromSharedPreferences(getContext());
        userPassword = MyHelper.getUserPasswordFromSharedPreferences(getContext());

        txtPerformance = (EditText) view.findViewById(R.id.txtPerformance);
        btnUpdate = (Button) view.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                performanceUpdate = txtPerformance.getText().toString();

                new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... params) {

                        HttpURLConnection urlConnection = null;
                        InputStream inputStream = null;
                        String result = "";
                        try {
                            URL url = new URL("http://35.184.144.226/shark2/admin/"+params[0]+"/"+params[1]+"/performance/"+performanceUpdate);
                            urlConnection = (HttpURLConnection) url.openConnection();
                            urlConnection.setRequestMethod("PUT");
                            urlConnection.setUseCaches(false);
                            urlConnection.connect();
                            inputStream = urlConnection.getInputStream();
                            byte [] buffer = new byte[128];
                            int actuallyRead = inputStream.read(buffer);
                            result = new String(buffer,0,actuallyRead);
                            inputStream.close();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }finally {
                            if (inputStream != null){
                                try {
                                    inputStream.close();
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
                    protected void onPostExecute(String s) {
                        Toast.makeText(getContext(), "Update Was : " + s, Toast.LENGTH_LONG).show();
                    }
                }.execute(userEmail,userPassword);
            }
        });
        return view;
    }

}
