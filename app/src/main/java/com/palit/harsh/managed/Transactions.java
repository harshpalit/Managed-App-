package com.palit.harsh.managed;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.palit.harsh.managed.CursorAdapter.TransactionCursorAdapter;
import com.palit.harsh.managed.Data.DataBaseContract.TransactionEntry;

public class Transactions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        String[] projection = {TransactionEntry._Id, TransactionEntry.Column_Emp_Id, TransactionEntry.Column_Date, TransactionEntry.Column_Item_Name, TransactionEntry.Column_Quantity, TransactionEntry.Column_Amount};
        Cursor c = getContentResolver().query(TransactionEntry.Content_Uri, projection, null, null, null);

        ListView listView = (ListView) findViewById(R.id.transaction_list);
        View empty = getLayoutInflater().inflate(R.layout.empty_transaction, null, false);
        ViewGroup viewGroup= (ViewGroup)listView.getParent();
        viewGroup.addView(empty);
        listView.setEmptyView(empty);
        TransactionCursorAdapter adapter = new TransactionCursorAdapter(getBaseContext(), c);
        listView.setAdapter(adapter);
    }

    public void NewTransaction(View view){
        startActivity(new Intent(this, NewTransaction.class));
    }
}

