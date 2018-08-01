package com.palit.harsh.managed.CursorAdapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.palit.harsh.managed.Data.DataBaseContract.SupplierEntry;
import com.palit.harsh.managed.R;

/**
 * Created by HARSH PALIT on 9/18/2017.
 */

public class SupplierCursorAdapter extends CursorAdapter {

    public SupplierCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_supplier, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView regtxtview = view.findViewById(R.id.supp_regno);
        TextView idtxtview = view.findViewById(R.id.supp_id);
        TextView nametxtview = view.findViewById(R.id.supp_name);
        TextView add1txtview = view.findViewById(R.id.supp_add1);
        TextView add2txtview= view.findViewById(R.id.supp_add2);
        TextView citytxtview = view.findViewById(R.id.supp_city);
        TextView statetxtview = view.findViewById(R.id.supp_state);
        TextView pintxtView = view.findViewById(R.id.supp_pincode);
        TextView contacttxtview = view.findViewById(R.id.supp_contact);
        TextView emailtxtview = view.findViewById(R.id.supp_email);

        int regIndex = cursor.getColumnIndex(SupplierEntry.Column_RegNo);
        int idIndex = cursor.getColumnIndex(SupplierEntry._Id);
        int nameIndex = cursor.getColumnIndex(SupplierEntry.Column_Name);
        int add1Index = cursor.getColumnIndex(SupplierEntry.Column_Address1);
        int add2Index = cursor.getColumnIndex(SupplierEntry.Column_Address2);
        int cityIndex = cursor.getColumnIndex(SupplierEntry.Column_Address3);
        int stateIndex = cursor.getColumnIndex(SupplierEntry.Column_Address4);
        int pinIndex = cursor.getColumnIndex(SupplierEntry.Column_Pin);
        int contactIndex = cursor.getColumnIndex(SupplierEntry.Column_ContactNo);
        int emailIndex =cursor.getColumnIndex(SupplierEntry.Column_Email);

        String reg = cursor.getString(regIndex);
        String id = cursor.getString(idIndex);
        String name = cursor.getString(nameIndex);
        String add1 = cursor.getString(add1Index);
        String add2 = cursor.getString(add2Index);
        String city = cursor.getString(cityIndex);
        String state = cursor.getString(stateIndex);
        String pin = cursor.getString(pinIndex);
        String contact = cursor.getString(contactIndex);
        String email = cursor.getString(emailIndex);

        regtxtview.setText(reg);
        idtxtview.setText(id);
        nametxtview.setText(name);
        add1txtview.setText(add1);
        add2txtview.setText(add2);
        citytxtview.setText(city +", ");
        statetxtview.setText(state + " - ");
        pintxtView.setText(pin);
        contacttxtview.setText(contact);
        emailtxtview.setText(email);


    }
}
