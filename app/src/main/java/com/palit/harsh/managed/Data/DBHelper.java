package com.palit.harsh.managed.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.palit.harsh.managed.Data.DataBaseContract.ItemEntry;

/**
 * Created by HARSH PALIT on 8/23/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Managed.db";
   // public static final String supplierTrigger = "Create trigger if not exists supplierTrigger AFTER INSERT ON "+ ItemEntry.Table_Name+ "WHEN ("+ ItemEntry.Table_Name + "."+ ItemEntry.Column_Supplier_iD + " != "+ DataBaseContract.SupplierEntry.Table_Name+"."+ DataBaseContract.SupplierEntry._Id+ ") BEGIN INSERT INTO "+ DataBaseContract.SupplierEntry.Table_Name;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(ItemEntry.SQL_CREATE_ITEMS_TABLE);
        sqLiteDatabase.execSQL(DataBaseContract.EmployeeEntry.SQL_CREATE_EMPLOYEE_TABLE);
        sqLiteDatabase.execSQL(DataBaseContract.TransactionEntry.SQL_CREATE_TRANSACTION_TABLE);
        sqLiteDatabase.execSQL(DataBaseContract.SupplierEntry.SQL_CREATE_SUPPLIERS_TABLE);
        sqLiteDatabase.execSQL(supplierTrigger());

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public String supplierTrigger(){
        String insertRecord = "CREATE TRIGGER if not exists add_student "
                + " AFTER DELETE "
                + " ON[item] "
                + " for each row "
                + " BEGIN "
                + " delete from item where supplier.id = 2;"
                + " END;";
        return insertRecord;
    }
}
