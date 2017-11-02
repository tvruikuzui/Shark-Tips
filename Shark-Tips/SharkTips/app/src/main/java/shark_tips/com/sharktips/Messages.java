package shark_tips.com.sharktips;


import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
public class Messages extends Fragment {

    private EditText txtSendMsgAdmin;
    private String sendMessage,getUserEmail,getPassword;
    private String adText,adUrl;
    private Button btnUpdateAd;
    private Button btnSendMsgAdmin;
    private EditText txtUpdateUrlAd,txtUpdateTextAd;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        txtSendMsgAdmin = (EditText) view.findViewById(R.id.txtSendMsgAdmin);
        btnSendMsgAdmin = (Button) view.findViewById(R.id.btnSendMsgAdmin);
        btnUpdateAd = (Button) view.findViewById(R.id.btnUpdateAd);
        txtUpdateUrlAd = (EditText) view.findViewById(R.id.txtUpdateUrlAd);
        txtUpdateTextAd = (EditText) view.findViewById(R.id.txtUpdateTextAd);
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
                            URL url = new URL("http://35.202.187.67/shark2/admin/" + params[0] + "/" + params[1] + "/message/");
                            urlConnection = (HttpURLConnection) url.openConnection();
                            urlConnection.setRequestMethod("POST");
                            urlConnection.setUseCaches(false);
                            urlConnection.setDoOutput(true);
                            urlConnection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
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

        btnUpdateAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adText = txtUpdateTextAd.getText().toString();
                adUrl = txtUpdateUrlAd.getText().toString();
                if (adText.length() == 0){
                    txtUpdateTextAd.setTextColor(Color.parseColor("#aa0036"));
                    txtUpdateTextAd.setText("No Data Was Entered.");
                    return;
                }else {

                    new AsyncTask<String, Void, String>() {
                        @Override
                        protected String doInBackground(String... params) {
                            String toSend = params[2] + "~" + params[3];
                            HttpURLConnection urlConnection = null;
                            InputStream inputStream = null;
                            OutputStream outputStream = null;
                            try {
                                URL url = new URL("http://35.202.187.67/shark2/admin/"+params[0]+"/"+params[1]+"/ad/");
                                urlConnection = (HttpURLConnection) url.openConnection();
                                urlConnection.setRequestMethod("PUT");
                                urlConnection.setUseCaches(false);
                                urlConnection.setDoOutput(true);
                                urlConnection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
                                urlConnection.connect();
                                outputStream = urlConnection.getOutputStream();
                                outputStream.write(toSend.getBytes());
                                outputStream.close();
                                inputStream = urlConnection.getInputStream();
                                byte[] buffer = new byte[128];
                                int a = inputStream.read(buffer);
                                inputStream.close();
                                return new String(buffer,0,a);
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }finally {
                                if (outputStream != null) {
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
                                if (urlConnection != null)
                                    urlConnection.disconnect();
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            Toast.makeText(getContext(), ""+s, Toast.LENGTH_SHORT).show();
                        }
                    }.execute(getUserEmail,getPassword,adText,adUrl);
                }
            }
        });

        return view;
    }

}
