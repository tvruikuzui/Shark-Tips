package shark_tips.com.sharktips;


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


interface AddUserListener{
    public void addUser(User user);
}

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {

    private Button btnRegister;
    private AddUserListener listener;
    private EditText txtName,txtLast,txtEmail,txtPhoneNumber,txtCountry,txtPassword;
    private User user;
    private CountryCodePicker ccp;
    private int getCountryCode;

    public void setListener(AddUserListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        txtName = (EditText) view.findViewById(R.id.txtName);
        txtLast = (EditText) view.findViewById(R.id.txtLast);
        txtEmail = (EditText) view.findViewById(R.id.txtEmail);
        txtPhoneNumber = (EditText) view.findViewById(R.id.txtPhoneNumber);
        txtPassword = (EditText) view.findViewById(R.id.txtPassword);
        ccp = (CountryCodePicker) view.findViewById(R.id.ccp);

        // Get the Country Code from Country Piker and store the value in getCountryCode.
        getCountryCode = ccp.getSelectedCountryCodeAsInt();

        // Add to the EditText the Country Code that was Selected.
        ccp.registerCarrierNumberEditText(txtPhoneNumber);
        // If user wrong and Switch Country Code , the portal was update.
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                // update the portal.
                getCountryCode = ccp.getSelectedCountryCodeAsInt();

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
                user.setPhoneNumber(Long.parseLong((getCountryCode+txtPhoneNumber.getText().toString())));
                if (user.checkValidPhoneNumber() == false){
                    txtPhoneNumber.setText("");
                    txtPhoneNumber.setHint("Phone Must have 7 numbers");
                    return;
                }
                user.setPassword(txtPassword.getText().toString());
                if (user.checkValidPassword() == false){
                    txtPassword.setText("");
                    txtPassword.setHint("Invalid Password");
                    return;
                }
                if (listener != null){
                    listener.addUser(user);
                }
            }
        });
        return view;
    }

}
