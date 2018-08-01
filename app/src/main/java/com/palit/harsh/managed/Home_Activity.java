package com.palit.harsh.managed;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.palit.harsh.managed.CursorAdapter.AvaialbilityCursorAdapter;
import com.palit.harsh.managed.Data.DataBaseContract;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;

public class Home_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView storeName, ownerEmail, welcomeText, UserWelcome;
    LinearLayout header;
    int year_x, month_x, day_x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //      .setAction("Action", null).show();
                Intent fileintent = new Intent(Intent.ACTION_GET_CONTENT);
                fileintent.setType("*/*");
                try {
                    startActivityForResult(fileintent, 1);
                } catch (ActivityNotFoundException e) {
                    //lbl.setText("No app found for importing the file.");
                }
            }
        });
        header = (LinearLayout) findViewById(R.id.Header);
        String[] projection1 = {DataBaseContract.ItemEntry._ID, DataBaseContract.ItemEntry.Column_Name, DataBaseContract.ItemEntry.Column_Description, DataBaseContract.ItemEntry.Column_Quantity};
        String[] arg = {"5"};
        Cursor cursor = getContentResolver().query(DataBaseContract.ItemEntry.Content_Uri, projection1, DataBaseContract.ItemEntry.Column_Quantity + "<=?", arg, null);
        ListView listView = (ListView) findViewById(R.id.stockList);
        AvaialbilityCursorAdapter adapter = new AvaialbilityCursorAdapter(this, cursor);
        listView.setAdapter(adapter);
        if (cursor.getCount() == 0)
            header.setVisibility(View.INVISIBLE);

        welcomeText = (TextView) findViewById(R.id.WelocmeMsg);
        UserWelcome = (TextView) findViewById(R.id.username);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View v = navigationView.getHeaderView(0);
        storeName = v.findViewById(R.id.OwnerNameView);
        ownerEmail = v.findViewById(R.id.OwnerEmailView);
        SharedPreferences userDetails = getSharedPreferences("UserDetailsPreference", Context.MODE_PRIVATE);
        String Storename = userDetails.getString("Storename", "Store Name");
        String ownerName = userDetails.getString("Name", "Owner");
        storeName.setText(Storename);
        ownerEmail.setText(userDetails.getString("Email", "storename@domainname.com"));
        welcomeText.setText(Storename);
        UserWelcome.setText("Hello " + ownerName);

        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(cal.YEAR);
        month_x = cal.get(cal.MONTH);
        day_x = cal.get(cal.DAY_OF_MONTH);
        StringBuilder curr = new StringBuilder().append(day_x).append("/").append(month_x + 1).append("/").append(year_x);
        String[] args = {curr.toString()};
        String[] projection = {DataBaseContract.TransactionEntry._Id, "sum(" + DataBaseContract.TransactionEntry.Column_Amount + ")" + " AS \"Total Amount\""};
        Cursor c = getContentResolver().query(DataBaseContract.TransactionEntry.Content_Uri, projection, DataBaseContract.TransactionEntry.Column_Date + "=?", args, null);
        int total = 0;
        c.moveToFirst();
        int amount = c.getInt(c.getColumnIndex("Total Amount"));
        TextView totaltxtview = (TextView) findViewById(R.id.totalsale);
        if (amount != 0)
            totaltxtview.setText("Your total sale for today is: Rs." + String.valueOf(amount));
        else
            totaltxtview.setText("You havent made any transactions today");
        c.close();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            viewTransactions();

        } else if (id == R.id.new_Transaction) {
            startActivity(new Intent(this, NewTransaction.class));
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            startActivity(new Intent(this, Additem_Activity.class));
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(this, DeleteItem_Activity.class));
        } else if (id == R.id.nav_slideshow) {
            startActivity(new Intent(this, View_Activity.class));
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void viewTransactions() {
        startActivity(new Intent(getBaseContext(), Transactions.class));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)
            return;
        switch (requestCode) {
            case 1:
                String filepath = data.getData().getPath();
                try {
                    if (resultCode == RESULT_OK) {
                        try {
                            FileReader file = new FileReader(filepath);
                            BufferedReader buffer = new BufferedReader(file);
                            ContentValues contentValues = new ContentValues();
                            String line = "";
                            while ((line = buffer.readLine()) != null) {
                                String[] str = line.split(",", 5);  // defining 3 columns with null or blank field //values acceptance
                                //Id, Company,Name,Price
                                String ItemName = str[0].toString();
                                String ItemPriceString = str[1].toString();
                                String QuantityString = str[2].toString();
                                String Description = str[3].toString();
                                String SupplierString = str[4].toString();

                                int ItemPrice = Integer.parseInt(ItemPriceString);
                                int Quantity = Integer.parseInt(QuantityString);
                                int Supplier = Integer.parseInt(SupplierString);
                                contentValues.put(DataBaseContract.ItemEntry.Column_Name, ItemName);
                                contentValues.put(DataBaseContract.ItemEntry.Column_Rate, ItemPrice);
                                contentValues.put(DataBaseContract.ItemEntry.Column_Quantity, Quantity);
                                contentValues.put(DataBaseContract.ItemEntry.Column_Description, Description);
                                contentValues.put(DataBaseContract.ItemEntry.Column_Supplier_iD, Supplier);

                            }
                        } catch (IOException e) {
                            Dialog d = new Dialog(this);
                            d.setTitle(e.getMessage().toString() + "first");
                            d.show();
                        }
                    }
                } catch (Exception ex) {
                    Dialog d = new Dialog(this);
                    d.setTitle(ex.getMessage().toString() + "second");
                    d.show();
                }

        }
    }
}