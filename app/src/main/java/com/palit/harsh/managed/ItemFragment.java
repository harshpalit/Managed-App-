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

import com.palit.harsh.managed.CursorAdapter.ItemCursorAdapter;
import com.palit.harsh.managed.Data.DataBaseContract;

public class ItemFragment extends Fragment {


    public ItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_item,container, false);

        String[] projection = {DataBaseContract.ItemEntry._ID, DataBaseContract.ItemEntry.Column_Name, DataBaseContract.ItemEntry.Column_Quantity, DataBaseContract.ItemEntry.Column_Rate, DataBaseContract.ItemEntry.Column_Supplier_iD};
        Cursor cursor = getActivity().getContentResolver().query(DataBaseContract.ItemEntry.Content_Uri, projection, null, null, null, null);

        ListView listView = rootview.findViewById(R.id.item_list);
        View empty = getActivity().getLayoutInflater().inflate(R.layout.empty_item, null, false);
        ViewGroup viewGroup= (ViewGroup)listView.getParent();
        viewGroup.addView(empty);
        listView.setEmptyView(empty);

        final ItemCursorAdapter adapter = new ItemCursorAdapter(getActivity().getBaseContext(), cursor);


        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long iD) {

                Cursor c = adapter.getCursor();
                c.moveToPosition(position);
                String id = c.getString(c.getColumnIndex(DataBaseContract.ItemEntry._ID));
                String name = c.getString(c.getColumnIndex(DataBaseContract.ItemEntry.Column_Name));
                String quantity = c.getString(c.getColumnIndex(DataBaseContract.ItemEntry.Column_Quantity));
                String rate = c.getString(c.getColumnIndex(DataBaseContract.ItemEntry.Column_Rate));
                Intent i = new Intent(getView().getContext(), EditItem_Activity.class);
                i.putExtra("id", id);
                i.putExtra("name", name);
                i.putExtra("quantity", quantity);
                i.putExtra("rate", rate);
                startActivity(i);

            }

        });
        // Inflate the layout for this fragment
        return rootview;
    }
}


