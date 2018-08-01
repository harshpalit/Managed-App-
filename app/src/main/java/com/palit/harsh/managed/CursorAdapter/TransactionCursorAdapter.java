package com.palit.harsh.managed.CursorAdapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.palit.harsh.managed.Data.DataBaseContract.TransactionEntry;
import com.palit.harsh.managed.R;

/**
 * Created by HARSH PALIT on 9/15/2017.
 */

public class TransactionCursorAdapter extends CursorAdapter {
    public TransactionCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_transactions, viewGroup ,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView idtxtView = view.findViewById(R.id.transaction_id);
        TextView emptxtView = view.findViewById(R.id.transaction_employeeid);
        TextView datetxtView = view.findViewById(R.id.transaction_date);
        TextView nametxtView = view.findViewById(R.id.transaction_item);
        TextView quantitytxtView = view.findViewById(R.id.transaction_quantity);
        TextView amounttxtView = view.findViewById(R.id.transaction_amount);

        int idColumnIndex = cursor.getColumnIndex(TransactionEntry._Id);
        int emp_idColumnIndex = cursor.getColumnIndex(TransactionEntry.Column_Emp_Id);
        int dateColumnInex = cursor.getColumnIndex(TransactionEntry.Column_Date);
        int nameColumnIndex = cursor.getColumnIndex(TransactionEntry.Column_Item_Name);
        int quantityColumnIndex = cursor.getColumnIndex(TransactionEntry.Column_Quantity);
        int amountColumnIndex = cursor.getColumnIndex(TransactionEntry.Column_Amount);

        String id = cursor.getString(idColumnIndex);
        String emp_id = cursor.getString(emp_idColumnIndex);
        String date = cursor.getString(dateColumnInex);
        String name = cursor.getString(nameColumnIndex);
        String quantity = cursor.getString(quantityColumnIndex);
        String amount = cursor.getString(amountColumnIndex);

        idtxtView.setText(id);
        emptxtView.setText("Emp_Id: "+ emp_id);
        datetxtView.setText("Date: "+ date);
        nametxtView.setText(name);
        quantitytxtView.setText(quantity);
        amounttxtView.setText(amount);
    }
}
