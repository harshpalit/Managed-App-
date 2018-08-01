package com.palit.harsh.managed;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.palit.harsh.managed.Data.DataBaseContract.SupplierEntry;

public class EditSupplier_Activity extends AppCompatActivity {

    Intent intent;
    String rowId ;
    Uri uri ;
    EditText maddress1, maddress2, maddress3, maddress4, mpin, mcontact, memail;
    TextView mName, mRegNo;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_supplier_);

        mName =(TextView) findViewById(R.id.item_name);
        maddress1 = (EditText) findViewById(R.id.address1);
        maddress2 = (EditText) findViewById(R.id.address2);
        maddress3 = (EditText) findViewById(R.id.address3);
        maddress4 = (EditText) findViewById(R.id.address4);
        mpin = (EditText) findViewById(R.id.pincode);
        mcontact = (EditText) findViewById(R.id.contactno);
        memail = (EditText) findViewById(R.id.email);
        mRegNo = (TextView) findViewById(R.id.regno);

        intent = getIntent();
        rowId = intent.getStringExtra("id");
        uri = Uri.withAppendedPath(SupplierEntry.Content_Uri,rowId);
        c = getContentResolver().query(uri, null, null, null, null, null);
        c.moveToPosition(0);

        mName.setText(c.getString(c.getColumnIndex(SupplierEntry.Column_Name)));
        mRegNo.setText(c.getString(c.getColumnIndex(SupplierEntry.Column_RegNo)));
        maddress1.setText(c.getString(c.getColumnIndex(SupplierEntry.Column_Address1)));
        maddress2.setText(c.getString(c.getColumnIndex(SupplierEntry.Column_Address2)));
        maddress3.setText(c.getString(c.getColumnIndex(SupplierEntry.Column_Address3)));
        maddress4.setText(c.getString(c.getColumnIndex(SupplierEntry.Column_Address4)));
        mpin.setText(c.getString(c.getColumnIndex(SupplierEntry.Column_Pin)));
        mcontact.setText(c.getString(c.getColumnIndex(SupplierEntry.Column_ContactNo)));
        memail.setText(c.getString(c.getColumnIndex(SupplierEntry.Column_Email)));
        c.close();
    }
    public void updateSupplier(View view){
        ContentValues values = new ContentValues();
        values.put(SupplierEntry.Column_Address1, maddress1.getText().toString());
        values.put(SupplierEntry.Column_Address2, maddress2.getText().toString());
        values.put(SupplierEntry.Column_Address3, maddress3.getText().toString());
        values.put(SupplierEntry.Column_Address4, maddress4.getText().toString());
        values.put(SupplierEntry.Column_Pin, mpin.getText().toString());
        values.put(SupplierEntry.Column_ContactNo, mcontact.getText().toString());
        values.put(SupplierEntry.Column_Email, memail.getText().toString());

        int returnid = getContentResolver().update(uri, values, null, null);
        startActivity(new Intent(EditSupplier_Activity.this, View_Activity.class));
    }
}
