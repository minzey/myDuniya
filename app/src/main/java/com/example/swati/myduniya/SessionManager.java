package com.example.swati.myduniya;

/**
 * Created by Swati on 21-11-2015.
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;
    //private String email;
    // Shared preferences file name
    private static final String PREF_NAME = "GreenBeanLogin";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static String KEY_EMAIL="email";
    private static String KEY_NAME="name";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        //editor.putString(KEY_EMAIL,email);
        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }
    public void setEmail(String email){
        editor.putString(KEY_EMAIL,email);
        //this.email=email;
        editor.commit();
    }
    public void setName(String name){
        editor.putString(KEY_NAME,name);
        editor.commit();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }
    public String getKeyEmail() {return pref.getString(KEY_EMAIL,"");}
    public String getKeyName(){return pref.getString(KEY_NAME,"");}
}