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
 * Created by HARSH PALIT on 10/3/2017.
 */

public class AvaialbilityCursorAdapter extends CursorAdapter {
    public AvaialbilityCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_stock_availability, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView nametxt = view.findViewById(R.id.name);
        TextView desctxt = view.findViewById(R.id.desc);
        TextView quantitytxt = view.findViewById(R.id.avl_quant);

        String name = cursor.getString(cursor.getColumnIndex(DataBaseContract.ItemEntry.Column_Name));
        String desc = cursor.getString(cursor.getColumnIndex(DataBaseContract.ItemEntry.Column_Description));
        int quantity = cursor.getInt(cursor.getColumnIndex(DataBaseContract.ItemEntry.Column_Quantity));

        nametxt.setText(name);
        desctxt.setText(desc);
        quantitytxt.setText("" + quantity);
    }
}
