package com.palit.harsh.managed;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.palit.harsh.managed.Data.DataBaseContract;

public class DeleteItem_Activity extends AppCompatActivity {

    private AdView mAdView;
    EditText mItem_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_item_);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");

        mAdView = findViewById(R.id.itemAddView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("1D5488851C6FA1F205A7A7023056C80D").build();
        mAdView.loadAd(adRequest);
        mItem_id = (EditText) findViewById(R.id.id_del);
    }

    public void deleteItem(View view) {

        String idString = mItem_id.getText().toString().trim();
        int id = Integer.parseInt(idString);


        Uri uri = Uri.withAppendedPath(DataBaseContract.ItemEntry.Content_Uri, idString);
        int r = getContentResolver().delete(uri,null,null);

        Toast.makeText(this, "1 item Deleted", Toast.LENGTH_SHORT).show();

    }
}
