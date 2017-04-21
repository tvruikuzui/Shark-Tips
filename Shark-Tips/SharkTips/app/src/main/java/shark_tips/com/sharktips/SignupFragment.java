package shark_tips.com.sharktips;



import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.hbb20.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


 interface SendLogListener{
    void sendLog(boolean isLogIn);

}

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {

    private static final String BASE_URL = "http://35.184.144.226/shark1/";
    private Button btnRegister;
    private EditText txtName,txtLast,txtEmail,txtPhoneNumber,txtCountry,txtPassword;
    private User user;
    private CountryCodePicker ccp;
    private String getCountryCode,userEmail;
    private boolean isAdmin = false,isSignUp;
    private String countryName;
    private SendLogListener logListener;
    private Spinner spnLang,spnLevel;
    private ArrayAdapter<CharSequence> langAdapter,levelAdapter;

    public void setLogListener(SendLogListener logListener) {
        this.logListener = logListener;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        txtName = (EditText) view.findViewById(R.id.txtName);
        txtLast = (EditText) view.findViewById(R.id.txtLast);
        txtCountry = (EditText) view.findViewById(R.id.txtCountry);
        txtEmail = (EditText) view.findViewById(R.id.txtEmail);
        txtPhoneNumber = (EditText) view.findViewById(R.id.txtPhoneNumber);
        txtPassword = (EditText) view.findViewById(R.id.txtPassword);
        spnLang = (Spinner) view.findViewById(R.id.spnLang);
        spnLevel = (Spinner) view.findViewById(R.id.spnLevel);
        ccp = (CountryCodePicker) view.findViewById(R.id.ccp);
        langAdapter = ArrayAdapter.createFromResource(getContext(),R.array.language,android.R.layout.simple_spinner_item);
        langAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLang.setAdapter(langAdapter);
        levelAdapter = ArrayAdapter.createFromResource(getContext(),R.array.TradingLevel,android.R.layout.simple_spinner_item);
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLevel.setAdapter(levelAdapter);
        // Get the Country Code from Country Piker and store the value in getCountryCode.
        getCountryCode = String.valueOf(ccp.getDefaultCountryCodeAsInt());
        countryName = ccp.getDefaultCountryName();
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
                    txtName.setHint("Invalid Name");
                    return;
                }
                user.setLastName(txtLast.getText().toString());
                if (user.checkValidUserLastName() == false){
                    txtLast.setText("");
                    txtLast.setHint("Invalid Last");
                    return;
                }
                user.setMail(txtEmail.getText().toString());
                if (user.checkValidMail() == false){
                    txtEmail.setText("");
                    txtEmail.setHint("Invalid Email");
                    return;
                }
                userEmail = txtEmail.getText().toString();
                MyHelper.saveUserEmailToSharedPreferences(getContext(),userEmail);

                user.setPhoneNumber(Long.parseLong((getCountryCode+txtPhoneNumber.getText().toString())));
                if (user.checkValidPhoneNumber() == false){
                    txtPhoneNumber.setText("");
                    txtPhoneNumber.setHint("Phone Must have 7 numbers");
                    return;
                }

                user.setCountryCode(String.valueOf(ccp.getSelectedCountryCodeAsInt()));

                user.setPassword(txtPassword.getText().toString());
                if (user.checkValidPassword() == false){
                    txtPassword.setText("");
                    txtPassword.setHint("Invalid Password");
                    return;
                }
                user.setCountry(countryName);
                if (user.checkValidCountryCode() == false){
                    txtCountry.setText("");
                    txtCountry.setHint("Invalid country name");
                    return;
                }


                if (logListener != null){
                    isSignUp = true;
                    MyHelper.saveToSharedPreferences(getContext(),isSignUp);
                    logListener.sendLog(isSignUp);
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
                                jsonObject.put("country",user.getCountry());
                                jsonObject.put("countryCode",user.getCountryCode());
                                jsonObject.put("password",user.getPassword());
                                jsonObject.put("email",user.getMail());
                                jsonObject.put("admin",user.getIsAdmin());
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
                        }
                    }.execute();
                }
            }
        });

        return view;
    }


}