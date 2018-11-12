package com.example.durandal.budgetingmadesimple;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This is an intermediary level between the database and the application
 */
public class Database extends SQLiteOpenHelper {

    // Add all member fields that are needed for proper interaction with the database
    public static final String DATABASE_NAME = "bms.db";
    public static final String TABLE_NAME = "User";
    public static final String COL_1 = "Id";
    public static final String COL_2 = "Username";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(new StringBuilder()
                .append(String.format("create table %s (", TABLE_NAME))
                .append(String.format("%s INTEGER PRIMARY KEY AUTOINCREMENT", COL_1))
                .append(String.format(", %s TEXT", COL_2))
                .append(")")
                .toString()
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Temporary
    public boolean insertData(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, username);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        return true;
    }

    // Temporary
    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
    }

    /**
     * TODO Creates an expenditure on the database.
     * @param inExp The expenditure to create
     * @return true if successful, false if not.
     */
    public boolean createExpOnDB(Expenditure inExp) {
        return true;
    }

    /**
     * TODO Deletes an expenditure off the database.
     * @param inExp Expenditure to delete.
     * @return true if successful, false if not.
     */
    public boolean deleteExpOnDB(Expenditure inExp) {
        return true;
    }

    /**
     * TODO Creates a new user account on the database.
     * @param userName name of account to be created.
     * @param password password of account to be created.
     * @return true if successful, false if not.
     */
    public boolean createNewAccount(String userName, String password) {
        return true;
    }


    /**
     * Todo - implement
     * Tests the provided username and password against database. If both valid, it fills the
     * inAccount object, and returns true.
     *
     * @param userName login name.
     * @param password login password.
     * @param inAccount out param to be filled if login is good.
     * @return true if login parameters are valid. false if login is not successful for any reason.
     */
    public boolean login(String userName, String password, Account inAccount) {
        return true;
    }

}
