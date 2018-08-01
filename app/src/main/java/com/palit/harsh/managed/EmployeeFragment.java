package com.palit.harsh.managed;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.palit.harsh.managed.CursorAdapter.EmployeeCursorAdapter;
import com.palit.harsh.managed.Data.DataBaseContract;

public class EmployeeFragment extends Fragment {


    public EmployeeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_employee, container, false);

        String[] projection = {DataBaseContract.EmployeeEntry._Id, DataBaseContract.EmployeeEntry.Column_Name, DataBaseContract.EmployeeEntry.Column_Facilities, DataBaseContract.EmployeeEntry.Column_Salary, DataBaseContract.EmployeeEntry.Column_Address};
        Cursor cursor = getActivity().getContentResolver().query(DataBaseContract.EmployeeEntry.Content_Uri, projection, null, null, null, null);

        ListView listView = rootview.findViewById(R.id.emp_list);
        View empty = getActivity().getLayoutInflater().inflate(R.layout.empty_employee, null, false);
        ViewGroup viewGroup = (ViewGroup) listView.getParent();
        viewGroup.addView(empty);
        listView.setEmptyView(empty);
        final EmployeeCursorAdapter adapter = new EmployeeCursorAdapter(getActivity().getBaseContext(), cursor);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long iD) {

                Cursor c = adapter.getCursor();
                c.moveToPosition(position);
                String id = c.getString(c.getColumnIndex(DataBaseContract.EmployeeEntry._Id));
                Intent i = new Intent(getView().getContext(), EditEmployee_Activity.class);
                i.putExtra("Id", id);
                startActivity(i);
            }
        });

        return rootview;

    }
}