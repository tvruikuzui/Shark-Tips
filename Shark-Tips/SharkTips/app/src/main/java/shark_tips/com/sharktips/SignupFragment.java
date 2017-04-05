package shark_tips.com.sharktips;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {

    private EditText txtName,txtLast,txtEmail,txtPhoneNumber,txtCountry,txtPassword;
    Button btnRegister;
    AddUserListener listener;
    private User user;

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
        txtCountry = (EditText) view.findViewById(R.id.txtCountry);
        txtPassword = (EditText) view.findViewById(R.id.txtPassword);

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
                user.setPhoneNumber(Integer.parseInt(txtPhoneNumber.getText().toString()));
                if (user.checkValidPhoneNumber() == false){
                    txtPhoneNumber.setText("");
                    txtPhoneNumber.setHint("Phone Must have 7 numbers");
                    return;
                }
                user.setCountry(txtCountry.getText().toString());
                if (user.checkValidCountryCode() == false){
                    txtCountry.setText("");
                    txtCountry.setHint("Invalid Country Code");
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

interface AddUserListener{
    public void addUser(User user);
}
