package shark_tips.com.sharktips;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by liranelyadumi on 5/7/17.
 */

public class UserEditPanel extends DialogFragment {

    
    private TextView lblEditUserEmailPanel,lblEditUserTsPanel;
    private CheckBox chkYes,chkNo,chkAddOrRemoveDays;
    private EditText txtUpdateUserDays;
    private Button btnSaveUserPanel,btnExitUserPanel;
    private User user;
    private UserNameEditListener listener;
    private boolean isPaid = false,hasDays = false;
    private int updateDays;
    private String getUserMail,getUserPassword;

    public void setListener(UserNameEditListener listener) {
        this.listener = listener;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_edit_panel,container,false);
        // Get user data from Shared.
        getUserMail = MyHelper.getUserEmailFromSharedPreferences(getContext());
        getUserPassword = MyHelper.getUserPasswordFromSharedPreferences(getContext());

        lblEditUserEmailPanel = (TextView) view.findViewById(R.id.lblEditUserEmailPanel);
        lblEditUserTsPanel = (TextView) view.findViewById(R.id.lblEditUserTsPanel);
        chkYes = (CheckBox) view.findViewById(R.id.chkYes);
        chkNo = (CheckBox) view.findViewById(R.id.chkNo);
        chkAddOrRemoveDays = (CheckBox) view.findViewById(R.id.chkAddOrRemoveDays);
        txtUpdateUserDays = (EditText) view.findViewById(R.id.txtUpdateUserDays);
        // Take the mail and days from user.
        lblEditUserEmailPanel.setText(user.getMail());
        lblEditUserTsPanel.setText(String.valueOf(user.getTimeStamp()));

        btnSaveUserPanel = (Button) view.findViewById(R.id.btnSaveUserPanel);
        btnSaveUserPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserPayment();
                addOrRemoveDays();
                updateDays = Integer.parseInt(txtUpdateUserDays.getText().toString());
                if (listener != null){
                    new AsyncTask<String, Void, String>() {
                        @Override
                        protected String doInBackground(String... params) {
                            HttpURLConnection urlConnection = null;
                            InputStream inputStream = null;
                            String result = null;
                            try {
                                URL url = new URL("http://35.184.144.226/shark2/admin/"
                                        + params[0]
                                        + "/"
                                        + params[1]
                                        + "/"
                                        + params[2]
                                        + "/"
                                        + updateDays
                                        + "/"
                                        + isPaid
                                        + "/"
                                        + hasDays + "/");
                                urlConnection = (HttpURLConnection) url.openConnection();
                                urlConnection.setRequestMethod("GET");
                                urlConnection.setUseCaches(false);
                                urlConnection.connect();
                                inputStream = urlConnection.getInputStream();
                                byte [] buffer = new byte[256];
                                int actuallyRead = inputStream.read(buffer);
                                result = new String(buffer,0,actuallyRead);
                                inputStream.close();
                                return result;
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            if (listener != null){
                                listener.showResult(s);
                            }
                        }
                    }.execute(getUserMail,getUserPassword,user.getMail());
                }



                dismiss();
            }
        });

        // Close the Edit Panel.
        btnExitUserPanel = (Button) view.findViewById(R.id.btnExitUserPanel);
        btnExitUserPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    // Check the status of user if paid or not.
    private void checkUserPayment() {
        if (chkYes.isChecked() && chkNo.isChecked()){
            Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
            isPaid = false;
            return;
        }

        if (chkYes.isChecked()){
            isPaid = true;

        }else {
            chkNo.isChecked();
            isPaid = false;
        }
    }

    // Check the status for add or remove days to user.
    private void addOrRemoveDays(){

        if (chkAddOrRemoveDays.isChecked()){
            hasDays = true;
        }else {
            hasDays = false;
        }
    }

    public interface UserNameEditListener{
        void editUser();
        void showResult(String result);

    }
}


