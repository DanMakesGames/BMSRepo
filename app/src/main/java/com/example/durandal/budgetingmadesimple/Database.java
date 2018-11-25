package com.example.durandal.budgetingmadesimple;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This class is an intermediary level between the database and the application
 */
public class Database extends SQLiteOpenHelper {

    // Define database
    public static final String DATABASE_NAME = "bms.db";

    // Define user table
    public static final String USER_TABLE_NAME = "User";
    public static final String USER_COL_1 = "UserId";
    public static final String USER_COL_1_TYPE = "INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String USER_COL_2 = "Username";
    public static final String USER_COL_2_TYPE = "TEXT";
    public static final String USER_COL_3 = "Password";
    public static final String USER_COL_3_TYPE = "TEXT";
    public static final String USER_COL_4 = "Email";
    public static final String USER_COL_4_TYPE = "TEXT";
    public static final String USER_COL_5 = "SecretQuestion";
    public static final String USER_COL_5_TYPE = "TEXT";
    public static final String USER_COL_6 = "SecretQuestionAnswer";
    public static final String USER_COL_6_TYPE = "TEXT";
    public static final String USER_COL_7 = "Budget";
    public static final String USER_COL_7_TYPE = "FLOAT";
    public static final String USER_COL_8 = "SavingsGoal";
    public static final String USER_COL_8_TYPE = "FLOAT";
    public static final String USER_COL_9 = "BankBalance";
    public static final String USER_COL_9_TYPE = "FLOAT";

    // Define expenditure table
    public static final String EXP_TABLE_NAME = "Expenditure";
    public static final String EXP_COL_1 = "ExpenditureId";
    public static final String EXP_COL_1_TYPE = "INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String EXP_COL_2 = "UserId";
    public static final String EXP_COL_2_TYPE = "INTEGER";
    public static final String EXP_COL_3 = "CategoryId";
    public static final String EXP_COL_3_TYPE = "INTEGER";
    public static final String EXP_COL_4 = "Amount";
    public static final String EXP_COL_4_TYPE = "FLOAT";
    public static final String EXP_COL_5 = "Date";
    public static final String EXP_COL_5_TYPE = "STRING";
    public static final String EXP_COL_6 = "IsRecurring";
    public static final String EXP_COL_6_TYPE = "BOOLEAN";

    // Define category table
    public static final String CAT_TABLE_NAME = "Category";
    public static final String CAT_COL_1 = "CategoryId";
    public static final String CAT_COL_1_TYPE = "INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String CAT_COL_2 = "UserId";
    public static final String CAT_COL_2_TYPE = "INTEGER";
    public static final String CAT_COL_3 = "Name";
    public static final String CAT_COL_3_TYPE = "STRING";
    public static final String CAT_COL_4 = "Budgt";
    public static final String CAT_COL_4_TYPE = "FLOAT";

    // Define supervisor table
    public static final String SUP_TABLE_NAME = "Supervisor";
    public static final String SUP_COL_1 = "SupervisorId";
    public static final String SUP_COL_1_TYPE = "INTEGER";
    public static final String SUP_COL_2 = "SupervisoreeId";
    public static final String SUP_COL_2_TYPE = "INTEGER";

    /**
     * Database constructor
     * @param context
     */
    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    /**
     * Executed when Database is created.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Build user table
        db.execSQL(new StringBuilder()
            .append(String.format("CREATE TABLE %s (", USER_TABLE_NAME))
            .append(String.format("%s %s", USER_COL_1, USER_COL_1_TYPE))
            .append(String.format(", %s %s", USER_COL_2, USER_COL_2_TYPE))
            .append(String.format(", %s %s", USER_COL_3, USER_COL_3_TYPE))
            .append(String.format(", %s %s", USER_COL_4, USER_COL_4_TYPE))
            .append(String.format(", %s %s", USER_COL_5, USER_COL_5_TYPE))
            .append(String.format(", %s %s", USER_COL_6, USER_COL_6_TYPE))
            .append(String.format(", %s %s", USER_COL_7, USER_COL_7_TYPE))
            .append(String.format(", %s %s", USER_COL_8, USER_COL_8_TYPE))
            .append(String.format(", %s %s", USER_COL_9, USER_COL_9_TYPE))
            .append(")")
            .toString()
        );

        // Build expenditure table
        db.execSQL(new StringBuilder()
            .append(String.format("CREATE TABLE %s (", EXP_TABLE_NAME))
            .append(String.format("%s %s", EXP_COL_1, EXP_COL_1_TYPE))
            .append(String.format(", %s %s", EXP_COL_2, EXP_COL_2_TYPE))
            .append(String.format(", %s %s", EXP_COL_3, EXP_COL_3_TYPE))
            .append(String.format(", %s %s", EXP_COL_4, EXP_COL_4_TYPE))
            .append(String.format(", %s %s", EXP_COL_5, EXP_COL_5_TYPE))
            .append(String.format(", %s %s", EXP_COL_6, EXP_COL_6_TYPE))
            .append(")")
            .toString()
        );

        // Build category table
        db.execSQL(new StringBuilder()
            .append(String.format("CREATE TABLE %s (", CAT_TABLE_NAME))
            .append(String.format("%s %s", CAT_COL_1, CAT_COL_1_TYPE))
            .append(String.format(", %s %s", CAT_COL_2, CAT_COL_2_TYPE))
            .append(String.format(", %s %s", CAT_COL_3, CAT_COL_3_TYPE))
            .append(String.format(", %s %s", CAT_COL_4, CAT_COL_4_TYPE))
            .append(")")
            .toString()
        );


        // Build supervior table
        db.execSQL(new StringBuilder()
            .append(String.format("CREATE TABLE %s (", SUP_TABLE_NAME))
            .append(String.format("%s %s", SUP_COL_1, SUP_COL_1_TYPE))
            .append(String.format(", %s %s", SUP_COL_2, SUP_COL_2_TYPE))
            .append(")")
            .toString()
        );
    }

    /**
     * Executed when Database is upgraded.
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EXP_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CAT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SUP_TABLE_NAME);
        onCreate(db);
    }

    /**
     * Creates a new user account on the database
     * @param username Username to be used for new user account
     * @param password Password to be used for new user account
     * @param email User's email
     * @param secretQuestion User's secret question
     * @param secretQuestionAnswer Answer to the user's secret question
     * @param budget User's budget.
     * @param savingsGoal User's savings goal
     * @param bankBalance User's current bank balance
     * @return true if successful, false if not
     */
    public boolean createUser(String username, String password, String email, String secretQuestion,
                              String secretQuestionAnswer, float budget, float savingsGoal,
                              float bankBalance) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COL_2, username);
        contentValues.put(USER_COL_3, password);
        contentValues.put(USER_COL_4, email);
        contentValues.put(USER_COL_5, secretQuestion);
        contentValues.put(USER_COL_6, secretQuestionAnswer);
        contentValues.put(USER_COL_7, budget);
        contentValues.put(USER_COL_8, savingsGoal);
        contentValues.put(USER_COL_9, bankBalance);

        long result = db.insert(USER_TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        return true;
    }

    /**
     * Get user account data corresponding to a specific username
     * @param username
     * @return Cursor object, which can be used access data
     */
    public Cursor getUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = new StringBuilder().append(String.format(
                "SELECT * FROM %s WHERE Username = \"%s\"", USER_TABLE_NAME, username)).toString();
        Cursor res = db.rawQuery(query, null);
        return res;
    }

    //TODO
    public boolean updateUser() { return true; }

    //TODO
    public boolean deleteUser() { return true; }

    /**
     * Creates an expenditure
     * @param userId User Id of user who the expenditure belongs
     * @param categoryId Category Id to which the expenditure belongs
     * @param amount Amount of expenditure in dollars
     * @param date Date of when expenditure was made
     * @param isRecurring Whether this expenditure is to occur again in the future
     * @return true if successful, false if not
     */
    public boolean createExpenditure(int userId, int categoryId, float amount, String date, boolean isRecurring) { 
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(EXP_COL_2, userId);
        contentValues.put(EXP_COL_3, categoryId);
        contentValues.put(EXP_COL_4, amount);
        contentValues.put(EXP_COL_5, date);
        contentValues.put(EXP_COL_6, isRecurring);

        long result = db.insert(EXP_TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        return true; 
    }

    /**
    * Get expenditures corresponding to a specific user
    * @param username of user
    * @return Cursor object, which can be used to acess data
    */
    public boolean getExpenditures(username) { 
        SQLiteDatabase db = this.getWritableDatabase();
        String query = new StringBuilder().append(String.format(
                "SELECT * FROM %s JOIN %s ON UserId WHERE username = \"%s\"", 
                EXP_TABLE_NAME, USER_TABLE_NAME, username)).toString();
        Cursor res = db.rawQuery(query, null);
        return res;
    }

    //TODO
    public boolean updateExpenditure() { return true; }

    //TODO
    public boolean deleteExpenditure() { return true; }

    //TODO
    public boolean createExpCategory() { return true; }

    //TODO
    public boolean getExpCategory() { return true; }

    //TODO
    public boolean updateExpCategory() { return true; }

    //TODO
    public boolean deleteExpCategory() { return true; }

    /**
     * TODO
     * Determine if the provided password for the provided username
     * @param userName login name.
     * @param password login password.
     * @return true if login parameters are valid. false if login is not successful for any reason
     */
    public boolean login(String userName, String password) {
        return true;
    }

}
