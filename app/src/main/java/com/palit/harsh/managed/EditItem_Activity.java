package com.palit.harsh.managed;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.palit.harsh.managed.Data.DataBaseContract;

public class EditItem_Activity extends AppCompatActivity {

    EditText mItemName;
    TextView mItemquantity;
    EditText mItemRate;
    EditText mItemDesc;
    Intent intent;
    int quantity = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item_);

        intent = getIntent();

        String nameString = intent.getStringExtra("name");
        String quantiyString = intent.getStringExtra("quantity");
        String rateString = intent.getStringExtra("rate");

        mItemName = (EditText) findViewById(R.id.item_name);
        mItemquantity = (TextView) findViewById(R.id.quantity);
        mItemRate = (EditText) findViewById(R.id.item_rate);
        mItemDesc = (EditText) findViewById(R.id.item_desc);


        mItemName.setText(nameString);
        mItemRate.setText(rateString);
        mItemquantity.setText(quantiyString);

    }


    public void UpdateItem(View view) {

        String name = mItemName.getText().toString().trim();
        String desc = mItemDesc.getText().toString().trim();
        String rateString = mItemRate.getText().toString();
        int rate = Integer.parseInt(rateString);
        String quantityString = mItemquantity.getText().toString();
        int quantity = Integer.parseInt(quantityString);

        ContentValues values = new ContentValues();
        values.put(DataBaseContract.ItemEntry.Column_Name, name);
        values.put(DataBaseContract.ItemEntry.Column_Description, desc);
        values.put(DataBaseContract.ItemEntry.Column_Quantity, quantity);
        values.put(DataBaseContract.ItemEntry.Column_Rate, rate);

        String id = intent.getStringExtra("id");
        Uri uri = Uri.withAppendedPath(DataBaseContract.ItemEntry.Content_Uri, id);

        int idreturn = getContentResolver().update(uri, values, null, null);

        if (idreturn != 0) {
            Toast.makeText(this, "Item Updated at row id" + idreturn, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(EditItem_Activity.this, View_Activity.class));
        }
    }

    public void incrementno(View view) {
        if (quantity < 100) {
            TextView number = (TextView) findViewById(R.id.quantity);
            quantity = quantity + 1;
            number.setText("" + quantity);
        } else
            Toast.makeText(this, R.string.toast_notallowed, Toast.LENGTH_SHORT).show();
    }

    public void decrementno(View view) {
        if (quantity > 1) {
            TextView number = (TextView) findViewById(R.id.quantity);
            quantity = quantity - 1;
            number.setText("" + quantity);
        } else
            Toast.makeText(this, R.string.toast_invalid_no, Toast.LENGTH_SHORT).show();
    }
}
