package com.palit.harsh.managed;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import java.util.Calendar;

import android.database.sqlite.SQLiteConstraintException;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.palit.harsh.managed.Data.DataBaseContract;

public class AddEmployee extends AppCompatActivity {

    EditText name;
    EditText salary;
    EditText address;
    EditText facilities;
    int year_x, month_x, day_x, cur;
    int DIALOG_ID = 1;
    EditText date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_employee);

        name =(EditText) findViewById(R.id.emp_name);
        salary = (EditText) findViewById(R.id.emp_salary);
        address = (EditText) findViewById(R.id.emp_add);
        facilities = (EditText) findViewById(R.id.emp_facilities);
        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(cal.YEAR);
        month_x = cal.get(cal.MONTH);
        day_x = cal.get(cal.DAY_OF_MONTH);
        date = (EditText) findViewById(R.id.employee_date);
        // perform click event on edit text
        date.setText(new StringBuilder().append(day_x).append("/").append(month_x + 1).append("/").append(year_x));
        date.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(DIALOG_ID);
                    }
                }
        );
    }
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID) {
            cur = DIALOG_ID;
            return new DatePickerDialog(this, dpickerListener, year_x, month_x, day_x);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener dpickerListener;

    {
        dpickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                year_x = year;
                month_x = month;
                day_x = dayOfMonth;

                if (cur == DIALOG_ID) {
                    date.setText(new StringBuilder().append(day_x).append("/").append(month_x + 1).append("/").append(year_x));
                    Toast.makeText(AddEmployee.this, day_x + "/" + (month_x + 1) + "/" + year_x, Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    public void insertEmployee(View view){
        String mName = name.getText().toString().trim();
        String mSalaryInt = salary.getText().toString().trim();
        int mSalary = Integer.parseInt(mSalaryInt);
        String mAddress = address.getText().toString().trim();
        String mFacilities = facilities.getText().toString().trim();
        String mdate = date.getText().toString().trim();

        ContentValues values = new ContentValues();
        values.put(DataBaseContract.EmployeeEntry.Column_Name, mName);
        values.put(DataBaseContract.EmployeeEntry.Column_Address,mAddress);
        values.put(DataBaseContract.EmployeeEntry.Column_Salary,mSalary);
        values.put(DataBaseContract.EmployeeEntry.Column_Age,mdate);
        values.put(DataBaseContract.EmployeeEntry.Column_Facilities,mFacilities);
        Uri returnuri = null;
        try {
            returnuri = getContentResolver().insert(DataBaseContract.EmployeeEntry.Content_Uri, values);
        }catch (SQLiteConstraintException e){
            Toast.makeText(this, "Insert Valid Salary", Toast.LENGTH_SHORT).show();
        }
        if (returnuri != null) {
            Toast.makeText(this, "Employee Added", Toast.LENGTH_SHORT).show();
            name.setText("");
            salary.setText("");
            address.setText("");
            facilities.setText("");
            date.setText("");
        }

    }
}
