package com.palit.harsh.managed.CursorAdapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.palit.harsh.managed.Data.DataBaseContract;
import com.palit.harsh.managed.R;

/**
 * Created by HARSH PALIT on 8/31/2017.
 */

public class ItemCursorAdapter extends CursorAdapter {
    public ItemCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup ,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView name = (TextView) view.findViewById(R.id.list_itemname);
        TextView quantity = view.findViewById(R.id.list_itemquantity);
        TextView rate = view.findViewById(R.id.list_itemrate);
        TextView id = view.findViewById(R.id.list_itemId);
        TextView supplier_id = view.findViewById(R.id.supplier_id);

        int nameColumnIndex = cursor.getColumnIndex(DataBaseContract.ItemEntry.Column_Name);
        int quantityColumnIndex = cursor.getColumnIndex(DataBaseContract.ItemEntry.Column_Quantity);
        int rateColumnIndex = cursor.getColumnIndex(DataBaseContract.ItemEntry.Column_Rate);
        int idColumnIndex = cursor.getColumnIndex(DataBaseContract.ItemEntry._ID);
        int supplierColumnIndex = cursor.getColumnIndex(DataBaseContract.ItemEntry.Column_Supplier_iD);

        String itemrate = cursor.getString(rateColumnIndex);
        String itemName = cursor.getString(nameColumnIndex);
        String itemquantity = cursor.getString(quantityColumnIndex);
        String itemid = cursor.getString(idColumnIndex);
        String supplierId = cursor.getString(supplierColumnIndex);

        id.setText(itemid);
        supplier_id.setText("Supplier Id:" +supplierId);
        name.setText(itemName);
        quantity.setText("Quantity:"+ itemquantity);
        rate.setText("Rate:"+itemrate);
    }
}
