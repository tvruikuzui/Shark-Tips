package shark_tips.com.sharktips;


import android.os.AsyncTask;
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
public class Messages extends Fragment {

    private EditText txtSendMsgAdmin;
    private Button btnSendMsgAdmin;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        txtSendMsgAdmin = (EditText) view.findViewById(R.id.txtSendMsgAdmin);
        btnSendMsgAdmin = (Button) view.findViewById(R.id.btnSendMsgAdmin);

        btnSendMsgAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... params) {
                        return null;
                    }
                }.execute();
            }
        });
        return view;
    }

}
