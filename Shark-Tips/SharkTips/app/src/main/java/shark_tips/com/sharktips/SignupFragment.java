package shark_tips.com.sharktips;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {

    private Button btnRegister;
    private EditText txtName,txtLast,txtEmail,txtPhoneNumber,txtCountry,txtPassword;
    private User user;
    private CountryCodePicker ccp;
    private String getCountryCode;
    private boolean isAdmin = false;
    private boolean isLogIn = false;
    private String countryName;
    private SharedPreferences preferences;
    private SendLogListener logListener;

    public void setLogListener(SendLogListener logListener) {
        this.logListener = logListener;
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
        ccp = (CountryCodePicker) view.findViewById(R.id.ccp);

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
                user.setPhoneNumber(Integer.parseInt((getCountryCode+txtPhoneNumber.getText().toString())));
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

                isLogIn = true;
                preferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
                if (preferences != null) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("log", isLogIn);
                    editor.commit();
                }

                if (logListener != null){
                    logListener.sendLog(isLogIn);
                    completeRegister();
                }
            }
        });

        return view;
    }

    private void completeRegister() {

        ConnectToServerThread c = new ConnectToServerThread();
        c.setUser(user);
        c.start();
    }

}

 interface SendLogListener{
    void sendLog(boolean isLogIn);

}