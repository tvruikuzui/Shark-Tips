package shark_tips.com.sharktips;



import android.content.Context;
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
    private boolean isLogIn = false;
    private LogInListener listener;
    private SharedPreferences preferences;

    public void setListener(LogInListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        txtLoginEmail = (EditText) view.findViewById(R.id.txtLogimEmail);
        txtLoginPassword = (EditText) view.findViewById(R.id.txtLoginPassword);

        btnLogin = (Button) view.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        HttpURLConnection urlConnection = null;
                        InputStream inputStream = null;
                        URL url = null;
                        String result="";
                        try {
                            url = new URL("http://35.184.144.226/shark1/logIn/"+params[0]+"/"+params[1]);
                            urlConnection = (HttpURLConnection) url.openConnection();
                            urlConnection.setUseCaches(false);
                            urlConnection.setRequestMethod("GET");
                            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                            urlConnection.connect();
                            inputStream = urlConnection.getInputStream();
                            byte [] buffer = new byte[128];
                            int a = inputStream.read(buffer);
                            result = new String(buffer,0,a);
                            Log.d("TAG",result);
                            inputStream.close();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (ProtocolException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return result;
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        switch (s){
                            case "not registered":
                                txtLoginEmail.setText(s);
                                break;
                            case "wrong password":
                                txtLoginPassword.setHint(s);
                                break;
                            case "ok":
                                isLogIn = true;
                                preferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
                                if (preferences != null) {
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putBoolean("log", isLogIn);
                                    editor.commit();
                                }
                                break;
                        }
                    }
                }.execute(txtLoginEmail.getText().toString(),txtLoginPassword.getText().toString());

                if (listener != null){
                    listener.logIn(isLogIn);
                }

            }
        });

        return view;
    }


}

interface LogInListener{
    void logIn(boolean isLogIn);
}