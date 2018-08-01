package com.palit.harsh.managed;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import static com.palit.harsh.managed.NewUserRegistration.Password;
import static com.palit.harsh.managed.NewUserRegistration.UserDetailspreference;
import static com.palit.harsh.managed.NewUserRegistration.Username;

public class Login_Activity extends AppCompatActivity {

    public static final String Loginpreference = "LoginPreference";
    public static final String Pref_Uname = "Username";
    public static final String Pref_Password = "Password";

    CheckBox checkremeberMe;
    EditText mUsername, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);
        switch (checkAppStart()) {
            case NORMAL:
                setContentView(R.layout.activity_login);

                mUsername = (EditText) findViewById(R.id.txtUserName);
                mPassword = (EditText) findViewById(R.id.txtPassword);
                checkremeberMe = (CheckBox) findViewById(R.id.remembermeCheck);
                SharedPreferences loginPreferences = getSharedPreferences(Loginpreference, Context.MODE_PRIVATE);
                mUsername.setText(loginPreferences.getString(Pref_Uname, ""));
                mPassword.setText(loginPreferences.getString(Pref_Password, ""));
                break;
            case FIRST_TIME:
                startActivity(new Intent(Login_Activity.this, NewUserRegistration.class));
                break;
        }
    }


    public void onLogin(View view) {
        String strUserName = mUsername.getText().toString().trim();
        String strPassword = mPassword.getText().toString().trim();
        if (null == strUserName || strUserName.length() == 0) {
            //  showToast("Enter Your Name");
            mUsername.requestFocus();
        } else if (null == strPassword || strPassword.length() == 0) {
            //      showToast("Enter Your Password");
            mPassword.requestFocus();
        } else {
            if (checkremeberMe.isChecked()) {
                SharedPreferences loginPreferences = getSharedPreferences(Loginpreference, Context.MODE_PRIVATE);
                loginPreferences.edit().putString(Pref_Uname, strUserName).putString(Pref_Password, strPassword).apply();
            } else {
                SharedPreferences loginPreferences = getSharedPreferences(Loginpreference, Context.MODE_PRIVATE);
                loginPreferences.edit().putString(Pref_Uname, "").putString(Pref_Password, "").apply();
            }
            SharedPreferences loginCredentials = getSharedPreferences(UserDetailspreference, Context.MODE_PRIVATE);
            if (strUserName.equals(loginCredentials.getString(Username,null)) && strPassword.equals(loginCredentials.getString(Password,null))) {

                startActivity(new Intent(getBaseContext(), Home_Activity.class));
                Toast.makeText(this, "Login Sucessfull", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Wrong username or password", Toast.LENGTH_LONG).show();
            }


        }
    }


    public enum AppStart {
        FIRST_TIME, FIRST_TIME_VERSION, NORMAL;
    }

    /**
     * The app version code (not the version name!) that was used on the last
     * start of the app.
     */
    private static final String LAST_APP_VERSION = "last_app_version";

    /**
     * Finds out started for the first time (ever or in the current version).<br/>
     * <br/>
     * Note: This method is <b>not idempotent</b> only the first call will
     * determine the proper result. Any subsequent calls will only return
     * {@link AppStart#NORMAL} until the app is started again. So you might want
     * to consider caching the result!
     *
     * @return the type of app start
     */
    public AppStart checkAppStart() {
        PackageInfo pInfo;
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        AppStart appStart = AppStart.NORMAL;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            int lastVersionCode = sharedPreferences
                    .getInt(LAST_APP_VERSION, -1);
            int currentVersionCode = pInfo.versionCode;
            appStart = checkAppStart(currentVersionCode, lastVersionCode);
            // Update version in preferences
            sharedPreferences.edit()
                    .putInt(LAST_APP_VERSION, currentVersionCode).commit();
        } catch (PackageManager.NameNotFoundException e) {
            Log.w(SyncStateContract.Constants._COUNT,
                    "Unable to determine current app version from pacakge manager. Defenisvely assuming normal app start.");
        }
        return appStart;
    }

    public AppStart checkAppStart(int currentVersionCode, int lastVersionCode) {
        if (lastVersionCode == -1) {
            return AppStart.FIRST_TIME;
        } else if (lastVersionCode < currentVersionCode) {
            return AppStart.FIRST_TIME_VERSION;
        } else if (lastVersionCode > currentVersionCode) {
            Log.w(SyncStateContract.Constants._COUNT, "Current version code (" + currentVersionCode
                    + ") is less then the one recognized on last startup ("
                    + lastVersionCode
                    + "). Defenisvely assuming normal app start.");
            return AppStart.NORMAL;
        } else {
            return AppStart.NORMAL;
        }
    }

    public void NoAccount(View view){
        startActivity(new Intent(Login_Activity.this, NewUserRegistration.class));
    }
}
