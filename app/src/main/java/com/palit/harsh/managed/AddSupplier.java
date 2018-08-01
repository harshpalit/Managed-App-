package com.palit.harsh.managed;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.palit.harsh.managed.Data.DataBaseContract.SupplierEntry;

public class AddSupplier extends AppCompatActivity {

    EditText mName, maddress1, maddress2, maddress3, maddress4, mpin, mcontact, memail, mregNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_supplier);

        mName =(EditText) findViewById(R.id.item_name);
        maddress1 = (EditText) findViewById(R.id.address1);
        maddress2 = (EditText) findViewById(R.id.address2);
        maddress3 = (EditText) findViewById(R.id.address3);
        maddress4 = (EditText) findViewById(R.id.address4);
        mpin = (EditText) findViewById(R.id.pincode);
        mcontact = (EditText) findViewById(R.id.contactno);
        memail = (EditText) findViewById(R.id.email);
        mregNo = (EditText) findViewById(R.id.regno);

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_emp) {
            startActivity(new Intent(getBaseContext(), AddEmployee.class));
        }
        else if (id == R.id.add_supplier){
            startActivity(new Intent(getBaseContext(), AddSupplier.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public void addSupplier(View view){

        String name = mName.getText().toString().trim();
        String address1 = maddress1.getText().toString().trim();
        String address2 = maddress2.getText().toString().trim();
        String address3 = maddress3.getText().toString().trim();
        String address4 = maddress4.getText().toString();
        String pinString = mpin.getText().toString();
        int pin = Integer.parseInt(pinString);
        String contact = mcontact.getText().toString().trim();
        String reg = mregNo.getText().toString().trim();
        String email = memail.getText().toString().trim();

        ContentValues values = new ContentValues();
        values.put(SupplierEntry.Column_Name, name);
        values.put(SupplierEntry.Column_Address1, address1);
        values.put(SupplierEntry.Column_Address2, address2);
        values.put(SupplierEntry.Column_Address3, address3);
        values.put(SupplierEntry.Column_Address4, address4);
        values.put(SupplierEntry.Column_Pin, pin);
        values.put(SupplierEntry.Column_ContactNo, contact);
        values.put(SupplierEntry.Column_RegNo, reg);
        values.put(SupplierEntry.Column_Email, email);

        try {

            Uri uri = getContentResolver().insert(SupplierEntry.Content_Uri, values);

            Toast.makeText(this, "Supplier Added", Toast.LENGTH_SHORT).show();
        }catch (SQLiteConstraintException e){
            Toast.makeText(this, "Supplier Already Exists", Toast.LENGTH_SHORT).show();
        }

    }
}
