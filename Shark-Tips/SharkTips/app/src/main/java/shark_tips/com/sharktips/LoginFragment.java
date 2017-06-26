package shark_tips.com.sharktips;




import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;



interface LogInListener{
    void checkUserLogFromLogin(boolean log);
}

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private EditText txtLoginEmail,txtLoginPassword;
    private Button btnLogin;
    private LogInListener listener;
    private boolean isLogin = false;
    private String getUserEmail,getUserPassword;


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
                getUserEmail = txtLoginEmail.getText().toString();
                getUserPassword = txtLoginPassword.getText().toString();
                new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        HttpURLConnection urlConnection = null;
                        InputStream inputStream = null;
                        URL url = null;
                        String result="";
                        try {
                            url = new URL("http://35.184.144.226/shark2/"+params[0]+"/"+params[1]+"/");
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
                                txtLoginEmail.setText("");
                                txtLoginEmail.setText(s);
                                break;
                            case "wrong password":
                                txtLoginEmail.setText("");
                                txtLoginPassword.setText(s);
                                break;
                            case "ok":
                                if (listener != null){
                                    isLogin = true;
                                    MyHelper.saveUserEmailToSharedPreferences(getContext(),getUserEmail);
                                    MyHelper.saveUserPasswordToSharedPreferences(getContext(),getUserPassword);
                                    MyHelper.saveToSharedPreferences(getContext(),isLogin);
                                    listener.checkUserLogFromLogin(isLogin);
                                    FcmServiceIdToken token = new FcmServiceIdToken();
                                    token.sendNewToken(getUserEmail, FirebaseInstanceId.getInstance().getToken());
                                }

                                break;
                        }
                    }
                }.execute(getUserEmail,getUserPassword);

            }
        });


        return view;
    }


}