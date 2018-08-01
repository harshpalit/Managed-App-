package com.palit.harsh.managed.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by HARSH PALIT on 8/28/2017.
 */

public class DataProvider extends ContentProvider {

    public static final String LOG_TAG = DataProvider.class.getSimpleName();


    private DBHelper mDBHelper;

    public static final int itemtable = 100;
    public static final int item_id = 101;
    public static final int emptable = 102;
    public static final int emp_id = 103;
    public static final int transactiontable = 104;
    public static final int transaction_id = 105;
    public static final int supplier_id = 106;
    public static final int suppliertable = 107;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(DataBaseContract.ItemEntry.Content_Authority, DataBaseContract.ItemEntry.Path_Item, itemtable);
        sUriMatcher.addURI(DataBaseContract.ItemEntry.Content_Authority, DataBaseContract.ItemEntry.Path_Item + "/#", item_id);
        sUriMatcher.addURI(DataBaseContract.EmployeeEntry.Content_Authority, DataBaseContract.EmployeeEntry.Path_Item, emptable);
        sUriMatcher.addURI(DataBaseContract.EmployeeEntry.Content_Authority, DataBaseContract.EmployeeEntry.Path_Item+ "/#", emp_id);
        sUriMatcher.addURI(DataBaseContract.EmployeeEntry.Content_Authority, DataBaseContract.TransactionEntry.Path_Item, transactiontable);
        sUriMatcher.addURI(DataBaseContract.EmployeeEntry.Content_Authority, DataBaseContract.TransactionEntry.Path_Item + "/#", transaction_id);
        sUriMatcher.addURI(DataBaseContract.SupplierEntry.Content_Authority, DataBaseContract.SupplierEntry.Path_Item, suppliertable);
        sUriMatcher.addURI(DataBaseContract.SupplierEntry.Content_Authority, DataBaseContract.SupplierEntry.Path_Item + "/#", supplier_id);
    }


    @Override
    public boolean onCreate() {

        mDBHelper = new DBHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {

        SQLiteDatabase database = mDBHelper.getReadableDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case itemtable:
                cursor = database.query(DataBaseContract.ItemEntry.Table_Name, strings, s, strings1, null, null, null);
                break;

            case item_id:
                s = DataBaseContract.ItemEntry._ID + "=?";
                strings1 = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(DataBaseContract.ItemEntry.Table_Name, strings, s, strings1, s1, null, null);
                break;

            case emptable:
                cursor = database.query(DataBaseContract.EmployeeEntry.Table_Name, strings, s, strings1, null, null, null);
                break;

            case transactiontable:
                cursor = database.query(DataBaseContract.TransactionEntry.Table_Name, strings, s, strings1, null, null, null);
                break;

            case transaction_id:
                s = DataBaseContract.TransactionEntry._Id+ "=?";
                strings1 = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(DataBaseContract.TransactionEntry.Table_Name, strings, s, strings1, null, null, null);
                break;

            case suppliertable:
                cursor = database.query(DataBaseContract.SupplierEntry.Table_Name, strings, s, strings1, null, null, null);
                break;

            case supplier_id:
                s = DataBaseContract.SupplierEntry._Id + "=?";
                strings1 = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(DataBaseContract.SupplierEntry.Table_Name, strings, s, strings1, null, null, null);
                break;

            default:
                throw new IllegalArgumentException("Cannot query given Uri " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        int match = sUriMatcher.match(uri);
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        switch (match) {
            case itemtable:
                return InsertItem(uri, contentValues);
            case emptable:

                long id = db.insertOrThrow(DataBaseContract.EmployeeEntry.Table_Name, null, contentValues);
                return ContentUris.withAppendedId(uri,id);
            case transactiontable:
                long tid = db.insertOrThrow(DataBaseContract.TransactionEntry.Table_Name, null, contentValues);
                return ContentUris.withAppendedId(uri,tid);

            case suppliertable:
                long sid = db.insertOrThrow(DataBaseContract.SupplierEntry.Table_Name, null, contentValues);
                return ContentUris.withAppendedId(uri, sid);

            default:
                throw new IllegalArgumentException("Uri not supported");
        }
    }

    private Uri InsertItem(Uri uri, ContentValues values) {
        SQLiteDatabase database = mDBHelper.getWritableDatabase();

        long id = database.insertOrThrow(DataBaseContract.ItemEntry.Table_Name, null, values);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert the row");
            return null;
        }
        getContext().getContentResolver().notifyChange(uri,null);

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase databse = mDBHelper.getWritableDatabase();
        int rowsdeleted=0;

        int match = sUriMatcher.match(uri);
        switch(match){
            case itemtable:
               rowsdeleted =  databse.delete(DataBaseContract.ItemEntry.Table_Name,s, strings);
                return rowsdeleted;
            case item_id:
                s = DataBaseContract.ItemEntry._ID + "=?";
                strings = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsdeleted = databse.delete(DataBaseContract.ItemEntry.Table_Name,s,strings);
                return rowsdeleted;
        }
        if (rowsdeleted!=0)
            getContext().getContentResolver().notifyChange(uri,null);

        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {

        SQLiteDatabase databse = mDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int itemsupdated, employeesupdated;

        switch (match) {
            case itemtable:
                itemsupdated = databse.update(DataBaseContract.ItemEntry.Table_Name, contentValues, s, strings);
                return itemsupdated;

            case item_id:
                s = DataBaseContract.ItemEntry._ID + "=?";
                strings = new String[]{String.valueOf(ContentUris.parseId(uri))};

                itemsupdated =  databse.update(DataBaseContract.ItemEntry.Table_Name, contentValues, s, strings);
                if(itemsupdated!=0)
                    getContext().getContentResolver().notifyChange(uri,null);

                return itemsupdated;

            case emptable:
                return databse.update(DataBaseContract.EmployeeEntry.Table_Name, contentValues, s, strings);

            case emp_id:
                s = DataBaseContract.EmployeeEntry._Id + "=?";
                strings = new String[]{String.valueOf(ContentUris.parseId(uri))};
                employeesupdated = databse.update(DataBaseContract.EmployeeEntry.Table_Name, contentValues, s, strings);
                return employeesupdated;

            case suppliertable:
                return databse.update(DataBaseContract.SupplierEntry.Table_Name, contentValues, s, strings);

            case supplier_id:
                s = DataBaseContract.SupplierEntry._Id + "=?";
                strings = new String[]{String.valueOf(ContentUris.parseId(uri))};

                return databse.update(DataBaseContract.SupplierEntry.Table_Name, contentValues, s, strings);

            default:
                throw new IllegalArgumentException("Update is not available for this ");
        }


    }
}
