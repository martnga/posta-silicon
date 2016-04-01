package com.craft.PostaEbox.sqliteStorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by mansa on 12/11/15.
 */
public class DatabaseOperations extends SQLiteOpenHelper {

    public static final int database_version = 1;
    private static final String KEY_ID = "order_id";
    public String CREATE_QUERY = "CREATE TABLE " + TableData.TableInfo.TABLE_NAME + "(" + TableData.TableInfo._ID + " INTEGER,"
            + TableData.TableInfo.PARTNER_ID + " TEXT,"
            + TableData.TableInfo.PARTNER_NAME + " TEXT,"
            + TableData.TableInfo.ACCOUNT_NUMBER + " TEXT );";


    public DatabaseOperations(Context context) {
        super(context, TableData.TableInfo.DATABASE_NAME, null, database_version);
        Log.d("Database Operation", "Database created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_QUERY);

        Log.d("Database Operation", "Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void saveInfo(DatabaseOperations dop, int _Id, String partnerID, String partnerName,
                          String accountNumber) {

        SQLiteDatabase SQ = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TableData.TableInfo._ID, _Id);
        cv.put(TableData.TableInfo.PARTNER_ID, partnerID);
        cv.put(TableData.TableInfo.PARTNER_NAME, partnerName);
        cv.put(TableData.TableInfo.ACCOUNT_NUMBER, accountNumber);


        long k = SQ.insert(TableData.TableInfo.TABLE_NAME, null, cv);

        Log.d("Database Operation", "One row inserted");



    }

    public Cursor getInformation(DatabaseOperations dop) {

        SQLiteDatabase SQ = dop.getReadableDatabase();
        String[] columns = {

                TableData.TableInfo._ID,
                TableData.TableInfo.PARTNER_ID,
                TableData.TableInfo.PARTNER_NAME,
                TableData.TableInfo.ACCOUNT_NUMBER

        };

        Cursor CR = SQ.query(TableData.TableInfo.TABLE_NAME, columns, null, null, null, null, null);
        return CR;

    }


}