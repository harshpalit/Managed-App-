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

import org.w3c.dom.Text;

/**
 * Created by HARSH PALIT on 9/7/2017.
 */

public class EmployeeCursorAdapter extends CursorAdapter {
    public EmployeeCursorAdapter(Context context , Cursor c){super(context , c, 0);}
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_employee, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView idtxtView = view.findViewById(R.id.emp_idlist);
        TextView nametxtView  =view.findViewById(R.id.emp_namelist);
        TextView salarytxtView = view.findViewById(R.id.emp_salarylist);
        TextView addtxtView = view.findViewById(R.id.emp_addlist);

        int addColumnIndex = cursor.getColumnIndex(DataBaseContract.EmployeeEntry.Column_Address);
        int salryColumnIndex = cursor.getColumnIndex(DataBaseContract.EmployeeEntry.Column_Salary);
        int idColumnIndex = cursor.getColumnIndex(DataBaseContract.EmployeeEntry._Id);
        int nameColumnIndex = cursor.getColumnIndex(DataBaseContract.EmployeeEntry.Column_Name);

        String add = cursor.getString(addColumnIndex);
        String salary = cursor.getString(salryColumnIndex);
        String id = cursor.getString(idColumnIndex);
        String name = cursor.getString(nameColumnIndex);

        addtxtView.setText(add);
        idtxtView.setText(id);
        nametxtView.setText(name);
        salarytxtView.setText(salary);
    }
}
