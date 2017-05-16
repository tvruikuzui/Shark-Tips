package shark_tips.com.sharktips;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



/**
 * A simple {@link Fragment} subclass.
 */
public class Messages extends Fragment {

    private EditText txtSendMsgAdmin;
    private String sendMessage,getUserEmail,getPassword;
    private Button btnSendMsgAdmin;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        txtSendMsgAdmin = (EditText) view.findViewById(R.id.txtSendMsgAdmin);
        btnSendMsgAdmin = (Button) view.findViewById(R.id.btnSendMsgAdmin);

        getUserEmail = MyHelper.getUserEmailFromSharedPreferences(getContext());
        getPassword = MyHelper.getUserPasswordFromSharedPreferences(getContext());
        btnSendMsgAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage = txtSendMsgAdmin.getText().toString();

                if (sendMessage.length() == 0)
                    return;
                new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        HttpURLConnection urlConnection = null;
                        OutputStream outputStream = null;
                        InputStream inputStream = null;
                        String result = "";
                        try {
                            URL url = new URL("http://35.184.144.226/shark2/admin/" + params[0] + "/" + params[1] + "/message/");
                            urlConnection = (HttpURLConnection) url.openConnection();
                            urlConnection.setRequestMethod("POST");
                            urlConnection.setUseCaches(false);
                            urlConnection.setDoOutput(true);
                            urlConnection.connect();
                            outputStream = urlConnection.getOutputStream();
                            outputStream.write(params[2].getBytes());
                            outputStream.close();
                            inputStream = urlConnection.getInputStream();
                            byte [] buffer = new byte[128];
                            int actuallyRead = inputStream.read(buffer);
                            inputStream.close();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }finally {
                            if (outputStream != null){
                                try {
                                    outputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

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
                }.execute(getUserEmail,getPassword,sendMessage);

                txtSendMsgAdmin.setText("");
            }

        });
        return view;
    }

}
