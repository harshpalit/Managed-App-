package com.palit.harsh.managed;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;

import java.text.SimpleDateFormat;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.CursorAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.palit.harsh.managed.Data.DataBaseContract.TransactionEntry;

import com.palit.harsh.managed.Data.DataBaseContract;

import java.util.Calendar;
import java.util.Date;

public class NewTransaction extends AppCompatActivity {

    int year_x, month_x, day_x;

    EditText itemidTxt,empidTxt, quantityTxt, rateTxt, dateText, timeTxt;
    TextView amountTxt, availaibilitytxt, storeName;
    Cursor cursor;
    String name, totalString;
    AutoCompleteTextView itemName;
    int Quantity, stockQuantity, rate, total, updatedQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_transaction);

        storeName = (TextView) findViewById(R.id.storename);
        Date anotherCurDate = new Date();
        SimpleDateFormat fromatter1 = new SimpleDateFormat("h:mm a");
        String TimeDateString = fromatter1.format(anotherCurDate);
        SharedPreferences userDetails = getSharedPreferences("UserDetailsPreference", Context.MODE_PRIVATE);
        String storename = userDetails.getString("Storename", "Store Name");
        storeName.setText(storename);

        itemidTxt = (EditText) findViewById(R.id.Item_Id);
        rateTxt = (EditText) findViewById(R.id.Item_rate);
        empidTxt = (EditText) findViewById(R.id.Employee_Id);
        quantityTxt = (EditText) findViewById(R.id.Quantity);
        amountTxt = (TextView) findViewById(R.id.Amount);
        dateText = (EditText) findViewById(R.id.Date);
        timeTxt = (EditText) findViewById(R.id.Time);
        availaibilitytxt = (TextView) findViewById(R.id.avl_Quantity);
        itemName = (AutoCompleteTextView) findViewById(R.id.Item_name);
        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(cal.YEAR);
        month_x = cal.get(cal.MONTH);
        day_x = cal.get(cal.DAY_OF_MONTH);
        dateText.setText(new StringBuilder().append(day_x).append("/").append(month_x + 1).append("/").append(year_x));
        dateText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(NewTransaction.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        selectedmonth = selectedmonth + 1;
                        dateText.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });
        timeTxt.setText(TimeDateString);
        timeTxt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(NewTransaction.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeTxt.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        cursor = getContentResolver().query(DataBaseContract.ItemEntry.Content_Uri, null, null, null, null);
        final ItemNameAdapter adapter = new ItemNameAdapter(this, cursor);
        itemName.setThreshold(0);
        itemName.setAdapter(adapter);
        itemName.setOnItemClickListener(adapter);

        quantityTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {
                    String quantityString = charSequence.toString();
                    Quantity = Integer.parseInt(quantityString);
                } else
                    Quantity = 0;
                total = Quantity * rate;
                totalString = Integer.toString(total);
            }

            @Override
            public void afterTextChanged(Editable editable) {


                if (Quantity<=stockQuantity) {
                    updatedQuantity = stockQuantity - Quantity;
                    availaibilitytxt.setText(updatedQuantity+"");
                    amountTxt.setText(totalString);
                }
                else{
                    Toast.makeText(getBaseContext(), "Select valid Quantity", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.transaction_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.Done) {
            addTransaction();
        } else if (id == R.id.Multiple_Items) {
            startActivity(new Intent(getBaseContext(), AddSupplier.class));
        }

        return super.onOptionsItemSelected(item);
    }

    class ItemNameAdapter extends CursorAdapter implements android.widget.AdapterView.OnItemClickListener {
        private ContentResolver mCR;

        public ItemNameAdapter(Context context, Cursor c) {
            super(context, c, 0);
            mCR = context.getContentResolver();
        }


        @Override
        public void bindView(View view, Context context, Cursor cursor) {


            ((TextView) view).setText(cursor.getString(1));
        }


        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            final LayoutInflater inflater = LayoutInflater.from(context);
            final TextView view = (TextView) inflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);


            view.setText(cursor.getString(1));

            return view;

        }

        @Override
        public String convertToString(Cursor cursor) {

            final int colIndex = cursor.getColumnIndexOrThrow(DataBaseContract.ItemEntry.Column_Name);
            return cursor.getString(colIndex);
        }

        public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
            if (getFilterQueryProvider() != null) {
                return getFilterQueryProvider().runQuery(constraint);
            }
            //String[] projection = {DataBaseContract.ItemEntry._ID, DataBaseContract.ItemEntry.Column_Name};
            StringBuilder buffer = null;
            String[] args = null;
            if (constraint != null) {
                buffer = new StringBuilder();
                buffer.append("UPPER(");
                buffer.append(DataBaseContract.ItemEntry.Column_Name);
                buffer.append(") GLOB ?");
                args = new String[]{constraint.toString().toUpperCase() + "*"};
            }

            return mCR.query(DataBaseContract.ItemEntry.Content_Uri, null, buffer == null ? null : buffer.toString(), args,
                    DataBaseContract.ItemEntry.Column_Name);
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long idl){
            Cursor c = (Cursor) adapterView.getItemAtPosition(position);
            String id = c.getString(c.getColumnIndex(DataBaseContract.ItemEntry._ID));
            itemidTxt.setText(id);
            name = c.getString(c.getColumnIndexOrThrow(DataBaseContract.ItemEntry.Column_Name));
            rate = c.getInt(c.getColumnIndexOrThrow(DataBaseContract.ItemEntry.Column_Rate));
            rateTxt.setText(rate+"");
            stockQuantity = c.getInt(c.getColumnIndexOrThrow(DataBaseContract.ItemEntry.Column_Quantity));
            availaibilitytxt.setText(stockQuantity+"");
        }

    }


    public void addTransaction() {
        String empidstring = empidTxt.getText().toString().trim();
        int empid = Integer.parseInt(empidstring);
        String itemidstring = itemidTxt.getText().toString().trim();
        int itemid = Integer.parseInt(itemidstring);
        String quantitystring = quantityTxt.getText().toString().trim();
        int quantity = Integer.parseInt(quantitystring);
        String ratestring = rateTxt.getText().toString().trim();
        int rate = Integer.parseInt(ratestring);
        String amountstring = amountTxt.getText().toString();
        int amount = Integer.parseInt(amountstring);
        String date = dateText.getText().toString().trim();
        String time = timeTxt.getText().toString().trim();

        ContentValues values = new ContentValues();

        values.put(TransactionEntry.Column_Item_Id, itemid);
        values.put(TransactionEntry.Column_Item_Name, name);
        values.put(TransactionEntry.Column_Quantity, quantity);
        values.put(TransactionEntry.Column_Rate, rate);
        values.put(TransactionEntry.Column_Amount, amount);
        values.put(TransactionEntry.Column_Emp_Id, empid);
        values.put(TransactionEntry.Column_Date, date);
        values.put(TransactionEntry.Column_Time, time);

        Uri uri = getContentResolver().insert(TransactionEntry.Content_Uri, values);

        ContentValues value = new ContentValues();
        value.put(DataBaseContract.ItemEntry.Column_Quantity, updatedQuantity);
        Uri uri1 = Uri.withAppendedPath(DataBaseContract.ItemEntry.Content_Uri, itemidstring);

        int r = getContentResolver().update(uri1, value, null, null);
        if (r != 0)
            Toast.makeText(this, "Transaction Sucessfull", Toast.LENGTH_SHORT).show();
    }
}


