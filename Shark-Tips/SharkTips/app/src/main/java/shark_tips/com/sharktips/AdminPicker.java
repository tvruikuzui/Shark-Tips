package shark_tips.com.sharktips;



import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminPicker extends Fragment {

    private Spinner spinnerAdmin;
    private Button btnUpdateUserAdmin,btnUpdateAd;
    private String userEmail,userPassword,userToBeAdmin,adText,adUrl;
    private ArrayAdapter<CharSequence> adminAdapter;
    private EditText txtMakeAdmin,txtUpdateUrlAd,txtUpdateTextAd;
    private TextView lblDescription;
    private boolean isAdmin = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_picker, container, false);
        userEmail = MyHelper.getUserEmailFromSharedPreferences(getContext());
        userPassword = MyHelper.getUserPasswordFromSharedPreferences(getContext());
        lblDescription = (TextView) view.findViewById(R.id.lblDescription);
        lblDescription.setVisibility(View.GONE);
        txtMakeAdmin = (EditText) view.findViewById(R.id.txtMakeAdmin);
        txtUpdateUrlAd = (EditText) view.findViewById(R.id.txtUpdateUrlAd);
        txtUpdateTextAd = (EditText) view.findViewById(R.id.txtUpdateTextAd);
        btnUpdateAd = (Button) view.findViewById(R.id.btnUpdateAd);
        spinnerAdmin = (Spinner) view.findViewById(R.id.spinnerAdmin);
        adminAdapter = ArrayAdapter.createFromResource(getContext(),R.array.admin,android.R.layout.simple_spinner_item);
        adminAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAdmin.setAdapter(adminAdapter);
        spinnerAdmin.getSelectedItemPosition();
        spinnerAdmin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    lblDescription.setVisibility(View.GONE);
                    return;
                }
                if (position == 1){
                    lblDescription.setVisibility(View.VISIBLE);
                    lblDescription.setTextColor(Color.parseColor("#aa0036"));
                    lblDescription.setText("NOTE: Your Going To Create Super Admin!");
                    return;
                }
                if (position == 2){
                    lblDescription.setVisibility(View.VISIBLE);
                    lblDescription.setTextColor(Color.parseColor("#8bba00"));
                    lblDescription.setText("NOTE: Your Going To Create Signal Admin!");
                    return;
                }
                if (position == 3){
                    lblDescription.setVisibility(View.VISIBLE);
                    lblDescription.setTextColor(Color.parseColor("#042d44"));
                    lblDescription.setText("NOTE: Remove User From Admin.");
                    return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnUpdateUserAdmin = (Button) view.findViewById(R.id.btnUpdateUserAdmin);
        btnUpdateUserAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer id = spinnerAdmin.getSelectedItemPosition();
                userToBeAdmin = txtMakeAdmin.getText().toString();
                if (txtMakeAdmin.length() == 0){
                    txtMakeAdmin.setText("");
                    txtMakeAdmin.setHint("No Email was Enter");
                    txtMakeAdmin.setHintTextColor(Color.RED);
                    return;
                }

                new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        HttpURLConnection urlConnection = null;
                        InputStream inputStream = null;
                        String result = "";
                        try {
                            URL url = new URL("http://35.184.144.226/shark2/admin/"+params[0]+"/"+params[1]+"/"+params[2]+"/"+Integer.valueOf(params[3]));
                            urlConnection = (HttpURLConnection) url.openConnection();
                            urlConnection.setRequestMethod("PUT");
                            urlConnection.setUseCaches(false);
                            urlConnection.connect();
                            inputStream = urlConnection.getInputStream();
                            byte [] buffer = new byte[256];
                            int actuallyRead = inputStream.read(buffer);
                            result = new String(buffer,0,actuallyRead);
                            inputStream.close();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }finally {
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
                        switch (result){
                            case "ok":
                                isAdmin = true;
                                MyHelper.saveIfIsAdminToSharedPreferences(getContext(),isAdmin);
                                Toast.makeText(getContext(),"Action was : " + result , Toast.LENGTH_SHORT).show();
                                break;
                            case "error":
                                isAdmin = false;
                                MyHelper.saveIfIsAdminToSharedPreferences(getContext(),isAdmin);
                                Toast.makeText(getContext(),"Action was : " + result , Toast.LENGTH_SHORT).show();
                                break;
                        }

                    }
                }.execute(userEmail,userPassword,userToBeAdmin, String.valueOf(id));
            }
        });


        btnUpdateAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adText = txtUpdateTextAd.getText().toString();
                adUrl = txtUpdateUrlAd.getText().toString();
                if (adText.length() == 0){
                    lblDescription.setVisibility(View.VISIBLE);
                    lblDescription.setTextColor(Color.parseColor("#aa0036"));
                    lblDescription.setText("No Data Was Entered.");
                    return;
                }else {
                    new AsyncTask<String, Void, String>() {
                        @Override
                        protected String doInBackground(String... params) {
                            HttpURLConnection urlConnection = null;
                            InputStream inputStream = null;
                            OutputStream outputStream = null;
                            try {
                                URL url = new URL("http://35.184.144.226/shark2/admin/"+params[0]+"/"+params[1]+"/ad/");
                                urlConnection = (HttpURLConnection) url.openConnection();
                                urlConnection.setRequestMethod("PUT");
                                urlConnection.setUseCaches(false);
                                urlConnection.setDoOutput(true);
                                urlConnection.connect();
                                outputStream = urlConnection.getOutputStream();
                                outputStream.write(params[2].getBytes());
                                outputStream.close();
                                inputStream = urlConnection.getInputStream();
                                byte[] buffer = new byte[128];
                                int a = inputStream.read(buffer);
                                inputStream.close();
                                return new String(buffer,0,a);
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }finally {
                                if (outputStream != null) {
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
                                if (urlConnection != null)
                                    urlConnection.disconnect();
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            Toast.makeText(getContext(), ""+s, Toast.LENGTH_SHORT).show();
                        }
                    }.execute(userEmail,userPassword,adText,adUrl);
                }
            }
        });


        return view;
    }


}



