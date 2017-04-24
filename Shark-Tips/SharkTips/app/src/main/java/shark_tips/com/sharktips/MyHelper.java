package shark_tips.com.sharktips;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by liranelyadumi on 4/23/17.
 */

public class MyHelper {

    // Save user log.
    static public void saveToSharedPreferences(Context context, boolean log){
        SharedPreferences preferences = context.getSharedPreferences("data",Context.MODE_PRIVATE);
        if (preferences != null){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("log",log);
            editor.commit();
        }else {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    // retrieve the user log.
    static public boolean getDataFromSharedPreferences(Context context){
        SharedPreferences preferences = context.getSharedPreferences("data",Context.MODE_PRIVATE);
        boolean getData = preferences.getBoolean("log",false);
        return getData;
    }

    // save user email.
    static public void saveUserEmailToSharedPreferences(Context context,String email){
        SharedPreferences preferences = context.getSharedPreferences("data",Context.MODE_PRIVATE);
        if (preferences != null){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("email",email);
            editor.commit();
        }else {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }

    }

    // retrieve user email.
    static public String getUserEmailFromSharedPreferences(Context context){
        SharedPreferences preferences = context.getSharedPreferences("data",Context.MODE_PRIVATE);
        if (preferences != null){
            String getEmail = preferences.getString("email","Office@shark-tips.com");
            return getEmail;
        }
        return String.valueOf(Log.d("TAG","ERROR"));
    }

    // crate
    static public void createFragment(Context context, Fragment fragment){

    }

}
