package shark_tips.com.sharktips;


import android.os.AsyncTask;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by liranelyadumi on 5/17/17.
 */

public class CheckPaidUsers {


    private String userEmail;
    private String userPassword;
    private ArrayList<User> users= new ArrayList<>();
    private ArrayList<User> unPaidUsers = new ArrayList<>();
    private GetUsersListListener listener;


    public void setListener(GetUsersListListener listener) {
        this.listener = listener;
    }

    public CheckPaidUsers(String userEmail, String userPassword) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }



    public void asyncUsers(){
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                HttpURLConnection urlConnection = null;
                InputStream inputStream = null;
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    URL url = new URL("http://35.202.187.67/shark2/admin/"+params[0]+"/"+params[1]+"/");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    urlConnection.setUseCaches(false);
                    urlConnection.connect();
                    inputStream = urlConnection.getInputStream();
                    byte [] buffer = new byte[512];
                    int actuallyRead;
                    while ((actuallyRead = inputStream.read(buffer)) != -1){
                        stringBuilder.append(new String(buffer,0,actuallyRead));
                    }
                    inputStream.close();
                    urlConnection.disconnect();
                    return stringBuilder.toString();

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


                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (jsonArray != null){
                    for (int i = 0;i < jsonArray.length();i++){
                        try {
                            JSONObject userObject = jsonArray.getJSONObject(i);
                            User user = new User(userObject.getString("name")
                                    ,userObject.getString("lastName")
                                    ,userObject.getString("email")
                                    ,userObject.getLong("addTimeToUser")
                                    ,userObject.getBoolean("paid"));
                            if (user.isPaid())
                                users.add(user);
                            else unPaidUsers.add(user);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (listener != null){
                    listener.getPaidUsers(users);
                    listener.getUnPaidUsers(unPaidUsers);
                }
            }
        }.execute(userEmail,userPassword);

    }
    interface GetUsersListListener{
        void getPaidUsers(ArrayList<User> users);
        void getUnPaidUsers(ArrayList<User> users);
    }

}

