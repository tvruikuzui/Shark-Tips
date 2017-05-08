package shark_tips.com.sharktips;

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


import java.util.ArrayList;

/**
 * Created by liranelyadumi on 5/7/17.
 */

public class UserEditPanel extends DialogFragment {

    
    private TextView lblEditUserEmailPanel,lblEditUserPaidPanel,lblEditUserTsPanel;
    private CheckBox chkYes,chkNo;
    private EditText txtUpdateUserDays;
    private Button btnSaveUserPanel,btnExitUserPanel;
    private User user;
    private UserNameEditListener listener;
    private boolean isPaid;
    private String updateDays;

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
        lblEditUserEmailPanel = (TextView) view.findViewById(R.id.lblEditUserEmailPanel);
        lblEditUserPaidPanel = (TextView) view.findViewById(R.id.lblEditUserPaidPanel);
        lblEditUserTsPanel = (TextView) view.findViewById(R.id.lblEditUserTsPanel);
        chkYes = (CheckBox) view.findViewById(R.id.chkYes);
        chkNo = (CheckBox) view.findViewById(R.id.chkNo);
        txtUpdateUserDays = (EditText) view.findViewById(R.id.txtUpdateUserDays);
        lblEditUserEmailPanel.setText(user.getMail());
        lblEditUserTsPanel.setText(String.valueOf(user.getTimeStamp()));

        btnSaveUserPanel = (Button) view.findViewById(R.id.btnSaveUserPanel);
        btnSaveUserPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserPayment();
                updateDays = txtUpdateUserDays.getText().toString();
                if (listener != null){

                }

                dismiss();
            }
        });
        
        btnExitUserPanel = (Button) view.findViewById(R.id.btnExitUserPanel);
        btnExitUserPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

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

    public static interface UserNameEditListener{

        public void editUser();
    }
}

