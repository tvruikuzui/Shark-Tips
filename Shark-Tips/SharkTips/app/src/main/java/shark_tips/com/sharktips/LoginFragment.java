package shark_tips.com.sharktips;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private EditText txtLoginEmail,txtLoginPassword;
    private Button btnLogin;
   // private User user = new User();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        txtLoginEmail = (EditText) view.findViewById(R.id.txtLogimEmail);
        txtLoginPassword = (EditText) view.findViewById(R.id.txtLoginPassword);

        btnLogin = (Button) view.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

}
