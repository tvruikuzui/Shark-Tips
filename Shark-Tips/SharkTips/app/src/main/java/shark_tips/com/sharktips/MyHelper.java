package shark_tips.com.sharktips;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by liranelyadumi on 4/23/17.
 */

public class MyHelper {

    // ---------------------------------------------------------------------------------------------
    // Start here User Log Handel
    // ---------------------------------------------------------------------------------------------

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

    // ---------------------------------------------------------------------------------------------
    // END
    // ---------------------------------------------------------------------------------------------


    // ---------------------------------------------------------------------------------------------
    // Start here User Email Handel
    // ---------------------------------------------------------------------------------------------

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
            String getEmail = preferences.getString("email","");
            return getEmail;
        }
        return String.valueOf(Log.d("TAG","ERROR"));
    }

    // ---------------------------------------------------------------------------------------------
    // END
    // ---------------------------------------------------------------------------------------------


    // ---------------------------------------------------------------------------------------------
    // Start here User Password Handel
    // ---------------------------------------------------------------------------------------------

    // save user Password.
    static public void saveUserPasswordToSharedPreferences(Context context,String password){
        SharedPreferences preferences = context.getSharedPreferences("data",Context.MODE_PRIVATE);
        if (preferences != null){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("password",password);
            editor.commit();
        }else {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }

    }

    // retrieve user Password
    static public String getUserPasswordFromSharedPreferences(Context context){
        SharedPreferences preferences = context.getSharedPreferences("data",Context.MODE_PRIVATE);
        if (preferences != null){
            String password = preferences.getString("password","");
            return password;
        }
        return String.valueOf(Log.d("TAG","ERROR"));
    }

    // ---------------------------------------------------------------------------------------------
    // END
    // ---------------------------------------------------------------------------------------------



    // ---------------------------------------------------------------------------------------------
    // Check if User Is Admin
    // ---------------------------------------------------------------------------------------------

    // save user Password.
    static public void saveIfIsAdminToSharedPreferences(Context context,boolean isAdmin){
        SharedPreferences preferences = context.getSharedPreferences("data",Context.MODE_PRIVATE);
        if (preferences != null){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isAdmin",isAdmin);
            editor.commit();
        }else {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }

    }

    // retrieve user Password
    static public boolean getIfIsAdminFromSharedPreferences(Context context){
        SharedPreferences preferences = context.getSharedPreferences("data",Context.MODE_PRIVATE);
            boolean isAdmin = preferences.getBoolean("isAdmin",false);
            return isAdmin;
    }

    // ---------------------------------------------------------------------------------------------
    // END
    // ---------------------------------------------------------------------------------------------



    // ---------------------------------------------------------------------------------------------
    // Check Time Stamp
    // ---------------------------------------------------------------------------------------------

    // save user Time.
    static public void saveTimeToSharedPreferences(Context context,int time){
        SharedPreferences preferences = context.getSharedPreferences("data",Context.MODE_PRIVATE);
        if (preferences != null){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("timeStamp",time);
            editor.commit();
        }else {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }

    }

    // retrieve user Time
    static public int getTimeFromSharedPreferences(Context context){
        SharedPreferences preferences = context.getSharedPreferences("data",Context.MODE_PRIVATE);
        int time = preferences.getInt("timeStamp",14);
        return time;
    }

    // ---------------------------------------------------------------------------------------------
    // END
    // ---------------------------------------------------------------------------------------------



}
