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

import com.palit.harsh.managed.CursorAdapter.SupplierCursorAdapter;
import com.palit.harsh.managed.Data.DataBaseContract.SupplierEntry;

public class SupplierFragment extends Fragment {


    public SupplierFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_supplier,container, false);

        Cursor cursor =getActivity().getContentResolver().query(SupplierEntry.Content_Uri,null,null,null,null,null);
        ListView listView = rootview.findViewById(R.id.supplier_list);
        View empty = getActivity().getLayoutInflater().inflate(R.layout.empty_suppliers, null, false);
        ViewGroup viewGroup= (ViewGroup)listView.getParent();
        viewGroup.addView(empty);
        listView.setEmptyView(empty);

        final SupplierCursorAdapter adapter  = new SupplierCursorAdapter(getActivity().getBaseContext(), cursor);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor c = adapter.getCursor();
                c.moveToPosition(i);
                String id = c.getString(c.getColumnIndex(SupplierEntry._Id));
                Intent intent = new Intent(getView().getContext(), EditSupplier_Activity.class);
                intent.putExtra("id", id);
                startActivity(intent);

            }
        });
        return rootview;
    }

}
