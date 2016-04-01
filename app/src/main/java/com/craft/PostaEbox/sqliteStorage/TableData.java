package com.craft.PostaEbox.sqliteStorage;

import android.provider.BaseColumns;

/**
 * Created by mansa on 12/11/15.
 */
public class TableData {

    public TableData(){}

    public static abstract class TableInfo implements BaseColumns
    {

        public static final String _ID = "_id";
        public static final String PARTNER_ID = "partner_id";
        public static final String PARTNER_NAME = "partner_name";
        public static final String ACCOUNT_NUMBER = "account_number";



        public static final String DATABASE_NAME = "users";
        public static final String TABLE_NAME = "user_details";

    }


}
