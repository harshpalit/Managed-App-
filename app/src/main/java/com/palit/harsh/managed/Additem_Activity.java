package com.palit.harsh.managed;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.palit.harsh.managed.Data.DataBaseContract;

/**
 * Created by HARSH PALIT on 8/8/2017.
 */

public class Additem_Activity extends AppCompatActivity {

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    int quantity = 0;
    EditText mItemName;
    EditText mItemDesc;
    EditText mItemRate;
    EditText mItemSupplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
        MobileAds.initialize(getApplicationContext(),
                "ca-app-pub-3940256099942544~3347511713 ");

        mItemName = (EditText) findViewById(R.id.item_name);
        mItemDesc = (EditText) findViewById(R.id.item_desc);
        mItemRate = (EditText) findViewById(R.id.item_rate);
        mItemSupplier = (EditText) findViewById(R.id.item_supplier);

        mAdView = findViewById(R.id.itemAddView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("1D5488851C6FA1F205A7A7023056C80D").build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice("1D5488851C6FA1F205A7A7023056C80D").build());

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
        } else if (id == R.id.add_supplier) {
            startActivity(new Intent(getBaseContext(), AddSupplier.class));
        }

        return super.onOptionsItemSelected(item);
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

    public void insertItem(View view) {

        String name = mItemName.getText().toString().trim();
        String desc = mItemDesc.getText().toString().trim();
        String rateString = mItemRate.getText().toString();

        String supplierString = mItemSupplier.getText().toString();


        if (name.length() == 0 || desc.length() == 0 || rateString.length() == 0 || supplierString.length() == 0) {
            Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
        } else if (Integer.parseInt(mItemRate.getText().toString()) <= 0) {
            Toast.makeText(this, "Enter valid Rate", Toast.LENGTH_SHORT).show();
        } else {

            int supplierId = Integer.parseInt(supplierString);
            int rate = Integer.parseInt(rateString);
            ContentValues values = new ContentValues();
            values.put(DataBaseContract.ItemEntry.Column_Name, name);
            values.put(DataBaseContract.ItemEntry.Column_Description, desc);
            values.put(DataBaseContract.ItemEntry.Column_Quantity, quantity);
            values.put(DataBaseContract.ItemEntry.Column_Rate, rate);
            values.put(DataBaseContract.ItemEntry.Column_Supplier_iD, supplierId);

            try {

                Uri uri = getContentResolver().insert(DataBaseContract.ItemEntry.Content_Uri, values);
                if(uri!=null) {

                    Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();
                }
            } catch (SQLiteConstraintException e) {
                Toast.makeText(this, "Item Already Exists", Toast.LENGTH_SHORT).show();
            }
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }

            Reset();
        }

    }

    private void Reset() {
        mItemName.setText("");
        mItemSupplier.setText("");
        mItemDesc.setText("");
        mItemRate.setText("");
    }
}

