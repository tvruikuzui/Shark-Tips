package shark_tips.com.sharktips;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.Logger;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hbb20.CountryCodePicker;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


 interface SignUpListener{
    void checkUserLogFromSignUp(boolean log);

}

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {

    public static final String BASE_URL = "http://35.184.144.226/shark2/";
    private Button btnRegister;
    private MaterialEditText txtName,txtLastName,txtEmail,txtPhoneNumber,txtPassword;
    private TextView txtCountry;
    private User user;
    private CountryCodePicker ccp;
    private String getCountryCode,userEmail,userPassword,countryName;
    private  boolean isLogin = false;
    private SignUpListener listener;
    private long phoneWithCode;
    private FirebaseAnalytics analytics;
    private AppEventsLogger logger;


    public void setListener(SignUpListener listener) {
        this.listener = listener;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        txtName = (MaterialEditText) view.findViewById(R.id.txtName);
        txtLastName = (MaterialEditText) view.findViewById(R.id.txtLastName);
        txtEmail = (MaterialEditText) view.findViewById(R.id.txtEmail);
        txtPhoneNumber = (MaterialEditText) view.findViewById(R.id.txtPhoneNumber);
        txtPassword = (MaterialEditText) view.findViewById(R.id.txtPassword);
        ccp = (CountryCodePicker) view.findViewById(R.id.ccp);
        txtCountry = (TextView) view.findViewById(R.id.txtCountry);

        analytics = FirebaseAnalytics.getInstance(getContext());
        logger = AppEventsLogger.newLogger(getContext());

        // Get the Country Code from Country Piker and store the value in getCountryCode.
        getCountryCode = String.valueOf(ccp.getDefaultCountryCodeAsInt());
        txtCountry.setText(countryName);
        // Add to the EditText the Country Code that was Selected.
        ccp.registerCarrierNumberEditText(txtPhoneNumber);
        // If user wrong and Switch Country Code , the portal was update.
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                // update the portal.
                getCountryCode = String.valueOf(ccp.getSelectedCountryCodeAsInt());
                countryName = ccp.getSelectedCountryName();
                txtCountry.setText(countryName);



            }
        });
        btnRegister = (Button) view.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = new User();
                user.setName(txtName.getText().toString());
                if (user.checkValidUserName() == false){
                    txtName.setText("");
                    txtName.setHintTextColor(Color.RED);
                    txtName.setHint("Invalid Name");
                    return;
                }

                user.setLastName(txtLastName.getText().toString());
                if (user.checkValidUserLastName() == false){
                    txtLastName.setText("");
                    txtLastName.setHintTextColor(Color.RED);
                    txtLastName.setHint("Invalid last Name");
                    return;
                }

                user.setMail(txtEmail.getText().toString());
                if (user.checkValidMail() == false){
                    txtEmail.setText("");
                    txtEmail.setHintTextColor(Color.RED);
                    txtEmail.setHint("Invalid Email");
                    return;
                }
                userEmail = txtEmail.getText().toString();

                user.setPassword(txtPassword.getText().toString());
                if (user.checkValidPassword() == false){
                    txtPassword.setText("");
                    txtPassword.setHintTextColor(Color.RED);
                    txtPassword.setHint("Invalid Password");
                    return;
                }
                userPassword = txtPassword.getText().toString();


                if (user.checkValidPhoneNumber() == false || txtPhoneNumber.length() == 0 || txtPhoneNumber.length() < 6) {
                    txtPhoneNumber.setText("");
                    txtPhoneNumber.setHint("Phone number is to short");
                    txtPhoneNumber.setHintTextColor(Color.RED);
                    return;
                }

                phoneWithCode = Long.parseLong(ccp.getSelectedCountryCodeAsInt()+txtPhoneNumber.getText().toString());
                user.setCountryCode(String.valueOf(ccp.getSelectedCountryCodeAsInt()));
                user.setPhoneNumber(phoneWithCode);




                if (listener != null){
                    isLogin = true;
                    listener.checkUserLogFromSignUp(isLogin);
                    new AsyncTask<Void, Void, String>() {
                        @Override
                        protected String doInBackground(Void... params) {
                            HttpURLConnection urlConnection = null;
                            InputStream inputStream = null;
                            OutputStream outputStream = null;
                            String result = "";
                            try {
                                URL url = new URL(BASE_URL);
                                urlConnection = (HttpURLConnection) url.openConnection();
                                urlConnection.setRequestMethod("POST");
                                urlConnection.setUseCaches(false);
                                urlConnection.setDoOutput(true);
                                urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                                urlConnection.connect();
                                outputStream = urlConnection.getOutputStream();
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("phoneNumber",user.getPhoneNumber());
                                jsonObject.put("name",user.getName());
                                jsonObject.put("lastName",user.getLastName());
                                jsonObject.put("country",countryName);
                                jsonObject.put("countryCode",user.getCountryCode());
                                jsonObject.put("password",user.getPassword());
                                jsonObject.put("email",user.getMail());
                                jsonObject.put("langSpeak","");
                                jsonObject.put("tradeLvl","");
                                jsonObject.put("paid",false);
                                jsonObject.put("token",FirebaseInstanceId.getInstance().getToken());
                                outputStream.write(jsonObject.toString().getBytes());
                                outputStream.close();
                                inputStream = urlConnection.getInputStream();
                                byte [] buffer = new byte[128];
                                int a = inputStream.read(buffer);
                                result = new String(buffer,0,a);
                                Log.d("TAG",result);
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
                        protected void onPostExecute(String result) {
                            super.onPostExecute(result);
                            if (result.equals("ok")) {
                                Bundle bundle = new Bundle();
                                bundle.putString(FirebaseAnalytics.Param.SIGN_UP_METHOD,"regular");
                                analytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle);
                                logger.logEvent("Register seccess");
                            }
                        }
                    }.execute();

                    MyHelper.saveUserEmailToSharedPreferences(getContext(),userEmail);
                    MyHelper.saveUserPasswordToSharedPreferences(getContext(),userPassword);
                    MyHelper.saveToSharedPreferences(getContext(),isLogin);
                    MyHelper.saveIfIsAdminToSharedPreferences(getContext(),false);

                }
            }
        });

        return view;
    }

}