package com.palit.harsh.managed;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.palit.harsh.managed.Data.DataBaseContract.EmployeeEntry;

public class EditEmployee_Activity extends AppCompatActivity {

    Intent intent;
    EditText nametxt, addresstxt, salarytxt, facilitiestxt;
    Cursor c;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee_);

        nametxt = (EditText) findViewById(R.id.emp_name);
        addresstxt = (EditText) findViewById(R.id.emp_address);
        salarytxt = (EditText) findViewById(R.id.emp_salary1);
        facilitiestxt = (EditText) findViewById(R.id.emp_facility);

        intent = getIntent();
        String id = intent.getStringExtra("Id");
        uri = Uri.withAppendedPath(EmployeeEntry.Content_Uri, id);
        c = getContentResolver().query(EmployeeEntry.Content_Uri, null, null, null, null, null);
        c.moveToFirst();
        nametxt.setText(c.getString(c.getColumnIndex(EmployeeEntry.Column_Name)));
        addresstxt.setText(c.getString(c.getColumnIndex(EmployeeEntry.Column_Address)));
        salarytxt.setText(c.getString(c.getColumnIndex(EmployeeEntry.Column_Salary)));
        facilitiestxt.setText(c.getString(c.getColumnIndex(EmployeeEntry.Column_Facilities)));

    }

    public void UpdateEmployee(View view) {

        String name = nametxt.getText().toString().trim();
        String address = addresstxt.getText().toString().trim();
        String salary = salarytxt.getText().toString().trim();
        String facility = facilitiestxt.getText().toString().trim();

        if (name==null || name.length()==0 || address==null || address.length()==0 || salary==null || salary.length()==0 || facility==null || facility.length()==0) {
            Toast.makeText(this, "Plaese enter all the values", Toast.LENGTH_SHORT).show();
        } else {
            ContentValues values = new ContentValues();
            values.put(EmployeeEntry.Column_Name, name);
            values.put(EmployeeEntry.Column_Address, address);
            values.put(EmployeeEntry.Column_Salary, salary);
            values.put(EmployeeEntry.Column_Facilities, facility);

            int returnid = getContentResolver().update(uri, values, null, null);
            Toast.makeText(this, name + " Updated", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(getBaseContext(), View_Activity.class));
        }
    }
}
