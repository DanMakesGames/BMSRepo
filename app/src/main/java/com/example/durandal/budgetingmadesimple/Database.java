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
    private static final String DATABASE_NAME = "bms.db";

    // Define user table
    private static final String USER_TABLE_NAME = "User";
    private static final String USER_COL_1 = "UserId";
    private static final String USER_COL_1_TYPE = "INTEGER PRIMARY KEY AUTOINCREMENT";
    private static final String USER_COL_2 = "Username";
    private static final String USER_COL_2_TYPE = "TEXT";
    private static final String USER_COL_3 = "Password";
    private static final String USER_COL_3_TYPE = "TEXT";
    private static final String USER_COL_4 = "Email";
    private static final String USER_COL_4_TYPE = "TEXT";
    private static final String USER_COL_5 = "SecretQuestion";
    private static final String USER_COL_5_TYPE = "TEXT";
    private static final String USER_COL_6 = "SecretQuestionAnswer";
    private static final String USER_COL_6_TYPE = "TEXT";
    private static final String USER_COL_7 = "Budget";
    private static final String USER_COL_7_TYPE = "FLOAT";
    private static final String USER_COL_8 = "SavingsGoal";
    private static final String USER_COL_8_TYPE = "FLOAT";
    private static final String USER_COL_9 = "BankBalance";
    private static final String USER_COL_9_TYPE = "FLOAT";

    // Define expenditure table
    private static final String EXP_TABLE_NAME = "Expenditure";
    private static final String EXP_COL_1 = "ExpenditureId";
    private static final String EXP_COL_1_TYPE = "INTEGER PRIMARY KEY AUTOINCREMENT";
    private static final String EXP_COL_2 = "UserId";
    private static final String EXP_COL_2_TYPE = "INTEGER";
    private static final String EXP_COL_3 = "CategoryId";
    private static final String EXP_COL_3_TYPE = "INTEGER";
    private static final String EXP_COL_4 = "Amount";
    private static final String EXP_COL_4_TYPE = "FLOAT";
    private static final String EXP_COL_5 = "Date";
    private static final String EXP_COL_5_TYPE = "STRING";
    private static final String EXP_COL_6 = "IsRecurring";
    private static final String EXP_COL_6_TYPE = "BOOLEAN";

    // Define category table
    private static final String CAT_TABLE_NAME = "Category";
    private static final String CAT_COL_1 = "CategoryId";
    private static final String CAT_COL_1_TYPE = "INTEGER PRIMARY KEY AUTOINCREMENT";
    private static final String CAT_COL_2 = "UserId";
    private static final String CAT_COL_2_TYPE = "INTEGER";
    private static final String CAT_COL_3 = "Name";
    private static final String CAT_COL_3_TYPE = "STRING";
    private static final String CAT_COL_4 = "Budgt";
    private static final String CAT_COL_4_TYPE = "FLOAT";

    // Define supervisor table
    private static final String SUP_TABLE_NAME = "Supervisor";
    private static final String SUP_COL_1 = "RelationshipId";
    private static final String SUP_COL_1_TYPE = "INTEGER PRIMARY KEY AUTOINCREMENT";
    private static final String SUP_COL_2 = "SupervisorId";
    private static final String SUP_COL_2_TYPE = "INTEGER";
    private static final String SUP_COL_3 = "SupervisoreeId";
    private static final String SUP_COL_3_TYPE = "INTEGER";

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


        // Build supervisor table
        db.execSQL(new StringBuilder()
            .append(String.format("CREATE TABLE %s (", SUP_TABLE_NAME))
            .append(String.format("%s %s", SUP_COL_1, SUP_COL_1_TYPE))
            .append(String.format(", %s %s", SUP_COL_2, SUP_COL_2_TYPE))
            .append(String.format(", %s %s", SUP_COL_3, SUP_COL_3_TYPE))
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
     * @param email Email to be used for new user account
     * @param secretQuestion Secret question to be used for new user account
     * @param secretQuestionAnswer Secret question answer to be used for new user account
     * @param budget Budget to used for new user account
     * @param savingsGoal Savings goal to be used for new user account
     * @param bankBalance Bank balance to be used for new user account
     * @return true if successful, false if not
     */
    public boolean createUser(String username, String password, String email, String secretQuestion,
                              String secretQuestionAnswer, float budget, float savingsGoal, float bankBalance) {
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
        return db.rawQuery(query, null);
    }

    /**
     * Update user account data in the database
     * @param userId Unique user ID for the user account
     * @param username Username to be used for the user account
     * @param password Password to be used for the user account
     * @param email Email to be used for the user account
     * @param secretQuestion Secret question to be used for the user account
     * @param secretQuestionAnswer Secret question answer to be used for the user account
     * @param budget Budget to used for the user account
     * @param savingsGoal Savings goal to be used for the user account
     * @return true if successful, false if not    
    */
    public boolean updateUser(int userId, String username, String password, String email, String secretQuestion,
                              String secretQuestionAnswer, float budget, float savingsGoal, float bankBalance) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COL_1, userId);
        contentValues.put(USER_COL_2, username);
        contentValues.put(USER_COL_3, password);
        contentValues.put(USER_COL_4, email);
        contentValues.put(USER_COL_5, secretQuestion);
        contentValues.put(USER_COL_6, secretQuestionAnswer);
        contentValues.put(USER_COL_7, budget);
        contentValues.put(USER_COL_8, savingsGoal);
        contentValues.put(USER_COL_9, bankBalance);

        int rowsChanged = db.update(USER_TABLE_NAME, contentValues, "UserId = ?",
                new String[] {Integer.toString(userId)});
        if (rowsChanged == 0)
            return false;
        return true;
    }

    /**
    * Delete user from the database
    * @param userId ID of the user to be deleted
    * @return true if successful, false if not
    */
    public boolean deleteUser(int userId) { 
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(USER_TABLE_NAME, "UserId = ?",
                new String[] {Integer.toString(userId)});
        if (rowsDeleted == 0)
            return false;
        return true; 
    }

    /**
     * Creates an expenditure
     * @param userId User ID of user to whom the expenditure belongs
     * @param categoryId Category ID to which the expenditure belongs
     * @param amount Amount of the expenditure in dollars
     * @param date Date of when expenditure was made. Instant.getEpochSeconds()
     * @param isRecurring Whether or not this expenditure is to occur again in the future
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
    * @param username  Username for user's account
    * @return Cursor object, which can be used to access data
    */
    public Cursor getExpenditures(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = new StringBuilder().append(String.format(
                "SELECT * FROM %s AS exp JOIN %s AS u ON exp.UserId = u.UserId WHERE u.Username = \"%s\"",
                EXP_TABLE_NAME, USER_TABLE_NAME, username)).toString();
        Cursor res = db.rawQuery(query, null);
        return res;
    }

    /**
     * Update an expenditure in the database
     * @param expenditureId Unique ID of the expenditure
     * @param userId User ID of user to whom the expenditure belongs
     * @param categoryId Category ID to which the expenditure belongs
     * @param amount Amount of the expenditure in dollars
     * @param date Date of when expenditure was made
     * @param isRecurring Whether or not this expenditure is to occur again in the future
     * @return true if successful, false if not
    */
    public boolean updateExpenditure(int expenditureId, int userId, int categoryId, float amount, String date,
                                     boolean isRecurring) { 
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(EXP_COL_1, expenditureId);
        contentValues.put(EXP_COL_2, userId);
        contentValues.put(EXP_COL_3, categoryId);
        contentValues.put(EXP_COL_4, amount);
        contentValues.put(EXP_COL_5, date);
        contentValues.put(EXP_COL_6, isRecurring);
        
        int rowsChanged = db.update(EXP_TABLE_NAME, contentValues, "expenditureId = ?",
                new String[] {Integer.toString(expenditureId)});
        if (rowsChanged == 0)
            return false;
        return true;
    }


    /**
    * Delete expenditure from the database
    * @param expenditureId ID of the expenditure to be deleted
    * @return true if successful, false if not
    */
    public boolean deleteExpenditure(int expenditureId) { 
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(EXP_TABLE_NAME, "ExpenditureId = ?",
                new String[] {Integer.toString(expenditureId)});
        if (rowsDeleted == 0)
            return false;
        return true; 
    }

    /**
    * Create a category for a specific user
    * @param userId User ID to whom category belongs
    * @param name Category name
    * @param budget User's budget for the category
    * @return true if successful, false if not
    */
    public boolean createExpCategory(int userId, String name, float budget) { 
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(CAT_COL_2, userId);
        contentValues.put(CAT_COL_3, name);
        contentValues.put(CAT_COL_4, budget);

        long result = db.insert(CAT_TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        return true;          
    }

    /**
    * Get categories corresponding to a specific user
    * @param username  Username for user's account
    * @return Cursor object, which can be used to access data
    */
    public Cursor getCategories(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = new StringBuilder().append(String.format(
                "SELECT * FROM %s AS cat JOIN %s AS u ON cat.UserId = u.UserId WHERE u.Username = \"%s\"",
                CAT_TABLE_NAME, USER_TABLE_NAME, username)).toString();
        Cursor res = db.rawQuery(query, null);
        return res;        
    }

    /**
    * Update a category for a specific user in the database   
    * @param categoryId Unique ID of the category
    * @param userId User ID to whom category belongs
    * @param name Category name
    * @param budget User's budget for the category
    * @return true if successful, false if not
    */
    public boolean updateExpCategory(int categoryId, int userId, String name, float budget) { 
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(CAT_COL_1, categoryId);
        contentValues.put(CAT_COL_2, userId);
        contentValues.put(CAT_COL_3, name);
        contentValues.put(CAT_COL_4, budget);

        int rowsChanged = db.update(CAT_TABLE_NAME, contentValues, "categoryId = ?",
                new String[] {Integer.toString(categoryId)});
        if (rowsChanged == 0)
            return false;
        return true;
    }

    /**
    * Delete a user's category from the database
    * @param categoryId ID of the category to be deleted
    * @return true if successful, false if not
    */
    public boolean deleteExpCategory(int categoryId) { 
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(CAT_TABLE_NAME, "CategoryId = ?",
                new String[] {Integer.toString(categoryId)});
        if (rowsDeleted == 0)
            return false;
        return true;  
    }

    /**
    * Create a supervisor-supervisee relationship in the database
    * @param supervisorId User ID of the supervisor
    * @param superviseeId User ID of the supervisee
    * @return true if successful, false if not
    */
    public boolean createSupervisor(int supervisorId, int superviseeId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(SUP_COL_2, supervisorId);
        contentValues.put(SUP_COL_3, superviseeId);

        long result = db.insert(SUP_TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        return true; 
    }

    /**
    * Get user IDs of all the accounts supervised by a supervisor account
    * @param supervisorId User ID of the supervisor account
    * @return Cursor object, which can be used access data
    */
    public Cursor getSupervisees(int supervisorId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = new StringBuilder().append(String.format(
                "SELECT SuperviseeId FROM %s WHERE SupervisorId = \"%s\"",
                SUP_TABLE_NAME, supervisorId)).toString();
        return db.rawQuery(query, null);
    }

    /**
    * Get user IDs of all the accounts supervising a supervisee account
    * @param superviseeId User ID of the supervisee account
    * @return Cursor object, which can be used access data
    */
    public Cursor getSupervisors(int superviseeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = new StringBuilder().append(String.format(
                "SELECT SupervisorId FROM %s WHERE SuperviseeId = \"%s\"",
                SUP_TABLE_NAME, superviseeId)).toString();
        return db.rawQuery(query, null);
    }

    /**
     * Delete a supervisor-supervisee relationship from the database
     * @param supervisorId User ID of supervisor account
     * @param superviseeId User ID of supervisee account
     * @return true if successful, false if not
     */
    public boolean deleteSupervisor(int supervisorId, int superviseeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(
                SUP_TABLE_NAME, "SupervisorId = ? AND SuperviseeId = ?",
                new String[] {Integer.toString(supervisorId), Integer.toString(superviseeId)});
        if (rowsDeleted == 0)
            return false;
        return true;
    }

    /**
     * Determine if the provided password for the provided username is valid
     * @param username login name.
     * @param password login password.
     * @return true if login parameters are valid, else false
     */
    public boolean login(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = new StringBuilder().append(String.format(
                "SELECT * FROM %s WHERE Username = \"%s\" AND Password = \"%s\"",
                USER_TABLE_NAME, username, password)).toString();
        Cursor res = db.rawQuery(query, null);
        if (res.getCount() == 0)
            // No entries returned, which have both the provided password and username
            return false;
        return true;
    }
}
