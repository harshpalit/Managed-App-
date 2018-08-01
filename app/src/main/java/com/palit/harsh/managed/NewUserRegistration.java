package com.palit.harsh.managed;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewUserRegistration extends AppCompatActivity {

    public static final String UserDetailspreference = "UserDetailsPreference";
    public static final String OwnerName = "Name";
    public static final String StoreName = "Storename";
    public static final String Reg_NO = "Registration No";
    public static final String StoreAddress = "Address";
    public static final String Username = "Username";
    public static final String Password = "Password";
    public static final String OwnerEmail = "Email";
    EditText rstorename, rstoreaddress, rRegNo, rOwnerName, rusername, rPassword, rConfirmpsswrd, remail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_registration);

        rstorename = (EditText) findViewById(R.id.Store_Name);
        rRegNo = (EditText) findViewById(R.id.Reg_No);
        rOwnerName = (EditText) findViewById(R.id.OwnerName);
        rstoreaddress = (EditText) findViewById(R.id.Store_Address);
        rusername = (EditText) findViewById(R.id.Username);
        rPassword = (EditText) findViewById(R.id.Password);
        rConfirmpsswrd = (EditText) findViewById(R.id.ConfrimPassword);
        remail = (EditText) findViewById(R.id.Email);
    }


    public void onRegister(View view){

        String storeName = rstorename.getText().toString().trim();
        String Regno = rRegNo.getText().toString().trim();
        String address = rstoreaddress.getText().toString().trim();
        String ownername = rOwnerName.getText().toString();
        String username = rusername.getText().toString().trim();
        String password = rPassword.getText().toString();
        String cnfrmpassword = rConfirmpsswrd.getText().toString().trim();
        String email = remail.getText().toString().trim();

        if(storeName.equals("")||Regno.equals("")||address.equals("")||ownername.equals("")||username.equals("")||password.equals("")||cnfrmpassword.equals("")||email.equals(""))
            Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show();

        else{
            if (password.equals(cnfrmpassword)){
                //SharedPreferences logincredentials = getSharedPreferences(Loginpreference, Context.MODE_PRIVATE);
                //logincredentials.edit().putString(Pref_Uname, username).putString(Pref_Password,password).apply();
                SharedPreferences userdetails = getSharedPreferences(UserDetailspreference,Context.MODE_PRIVATE);
                userdetails.edit().putString(StoreName, storeName).putString(Reg_NO, Regno).putString(StoreAddress, address).putString(OwnerName,ownername).putString(OwnerEmail,email).putString(Username, username).putString(Password, password).apply();
                startActivity(new Intent(NewUserRegistration.this, Home_Activity.class));
               /* mUsername.setText(username);
                mPassword.setText(password);*/

            }
            else
                Toast.makeText(this, "Password do not match", Toast.LENGTH_SHORT).show();
        }

    }
}
