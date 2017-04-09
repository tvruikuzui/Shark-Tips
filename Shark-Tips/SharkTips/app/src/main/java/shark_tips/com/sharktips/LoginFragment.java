package shark_tips.com.sharktips;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import java.net.ProtocolException;
import java.net.URL;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private EditText txtLoginEmail,txtLoginPassword;
    private Button btnLogin;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        txtLoginEmail = (EditText) view.findViewById(R.id.txtLogimEmail);
        txtLoginPassword = (EditText) view.findViewById(R.id.txtLoginPassword);

        btnLogin = (Button) view.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<String, Void, Void>() {
                    @Override
                    protected Void doInBackground(String... params) {
                        HttpURLConnection urlConnection = null;
                        InputStream inputStream = null;
                        OutputStream outputStream = null;
                        URL url = null;
                        try {
                            url = new URL("http://35.187.25.133/shark1/Users/logIn/"+params[0]+"/"+params[1]);
                            urlConnection = (HttpURLConnection) url.openConnection();
                            urlConnection.setUseCaches(false);
                            urlConnection.setRequestMethod("GET");
                            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                            urlConnection.connect();
                            inputStream = urlConnection.getInputStream();
                            byte [] buffer = new byte[128];
                            int a = inputStream.read(buffer);
                            String result = new String(buffer,0,a);
                            Log.d("TAG",result);
                            inputStream.close();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (ProtocolException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return null;
                    }
                }.execute(txtLoginEmail.getText().toString(),txtLoginPassword.getText().toString());
            }
        });


        return view;
    }


}
