package com.palit.harsh.managed.Data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by HARSH PALIT on 8/23/2017.
 */

public class DataBaseContract {

    private DataBaseContract(){}

    public static class ItemEntry implements BaseColumns{

        public static final String Table_Name = "item";
        public static final String _ID = BaseColumns._ID;
        public static final String Column_Name = "name";
        public static final String Column_Rate = "rate";
        public static final String Column_Quantity = "quantity";
        public static final String Column_Description = "description";
        public static final String Column_Supplier_iD = "supplier_id";

        public static final String SQL_CREATE_ITEMS_TABLE = "CREATE TABLE " + Table_Name + "(" +_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Column_Name + " TEXT NOT NULL UNIQUE, " +
                Column_Rate + " INTEGER NOT NULL, " +
                Column_Quantity + " INTEGER NOT NULL, " +
                Column_Description + " TEXT, " +
                Column_Supplier_iD + " INTEGER NOT NULL, FOREIGN KEY(supplier_id) REFERENCES suppliers(Id)); ";


        public static final String Content_Authority = "com.palit.harsh.managed";
        public static final Uri Base_Content_Uri = Uri.parse("content://" + Content_Authority );
        public static final String Path_Item = "item";
        public static final Uri Content_Uri = Uri.withAppendedPath(Base_Content_Uri, Path_Item);
    }

    public static class EmployeeEntry implements BaseColumns{

        public static final String Table_Name = "employee";
        public static final String _Id = BaseColumns._ID;
        public static final String Column_Name = "name";
        public static final String Column_DOB = "DOB";
        public static final String Column_Address = "address";
        public static final String Column_Age = "age";
        public static final String Column_Salary = "salary";
        public static final String Column_Facilities = "facilities";

        public static final String SQL_CREATE_EMPLOYEE_TABLE = "CREATE TABLE " + Table_Name + "(" + _Id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                Column_Name + " TEXT NOT NULL, " +
                Column_DOB + " TEXT, " +
                Column_Age + " INTEGER, " +
                Column_Salary + " REAL NOT NULL CHECK(" + Column_Salary+">0), "+
                Column_Address + " TEXT, " +
                Column_Facilities + " TEXT );";

        public static final String Content_Authority = "com.palit.harsh.managed";
        public static final Uri Base_Content_Uri = Uri.parse("content://" + Content_Authority );
        public static final String Path_Item = "employee";
        public static final Uri Content_Uri = Uri.withAppendedPath(Base_Content_Uri, Path_Item);


    }

    public static class TransactionEntry implements BaseColumns{

        public static final String Table_Name = "transactions";
        public static final String _Id = BaseColumns._ID;
        public static final String Column_Item_Id = "Item_Id";
        public static final String Column_Item_Name = "Description";
        public static final String Column_Quantity = "Quantity";
        public static final String Column_Rate = "Rate";
        public static final String Column_Amount = "Total";
        public static final String Column_Emp_Id = "Employee_Id";
        public static final String Column_Date = "Date";
        public static final String Column_Time = "Time";

        public static final String SQL_CREATE_TRANSACTION_TABLE = "CREATE TABLE " + Table_Name + "(" + _Id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
                Column_Date + " TEXT NOT NULL, " +
                Column_Time + " TEXT NOT NULL, " +
                Column_Item_Id + " INTEGER NOT NULL, " +
                Column_Item_Name + " TEXT NOT NULL, " +
                Column_Quantity + " INTEGER NOT NULL, " +
                Column_Rate + " INTEGER NOT NULL, " +
                Column_Amount + " INTEGER NOT NULL, " +
                Column_Emp_Id + " INTEGER );";

        public static final String Content_Authority = "com.palit.harsh.managed";
        public static final Uri Base_Content_Uri = Uri.parse("content://" + Content_Authority );
        public static final String Path_Item = "transactions";
        public static final Uri Content_Uri = Uri.withAppendedPath(Base_Content_Uri, Path_Item);
    }

    public static class SupplierEntry implements BaseColumns{

        public static final String Table_Name = "suppliers";
        public static final String _Id = BaseColumns._ID;
        public static final String Column_Name = "name";
        public static final String Column_Address1 = "addressline1";
        public static final String Column_Address2 = "addressline2";
        public static final String Column_Address3 = "City";
        public static final String Column_Address4 = "State";
        public static final String Column_Pin = "pincode";
        public static final String Column_ContactNo = "Contact";
        public static final String Column_Email = "Email";
        public static final String Column_RegNo = "Reg_No";
        public static final String Column_Credit = "Credit_Amount";

        public static final String SQL_CREATE_SUPPLIERS_TABLE = "CREATE TABLE " + Table_Name + " (" + _Id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                Column_Name + " TEXT NOT NULL, " +
                Column_Address1 + " TEXT NOT NULL, " +
                Column_Address2 + " TEXT, " +
                Column_Address3 + " TEXT NOT NULL, " +
                Column_Address4 + " TEXT NOT NULL, " +
                Column_Pin + " TEXT NOT NULL, " +
                Column_ContactNo + " TEXT NOT NULL, " +
                Column_Email + " TEXT, " +
                Column_RegNo + " TEXT NOT NULL UNIQUE, " +
                Column_Credit + " INTEGER );";

        public static final String Content_Authority = "com.palit.harsh.managed";
        public static final Uri Base_Content_Uri = Uri.parse("content://" + Content_Authority );
        public static final String Path_Item = "suppliers";
        public static final Uri Content_Uri = Uri.withAppendedPath(Base_Content_Uri, Path_Item);

    }
}
