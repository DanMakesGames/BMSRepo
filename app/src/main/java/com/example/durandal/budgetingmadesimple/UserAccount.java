package com.example.durandal.budgetingmadesimple;

/*
 * Stores all data related to the account we logged into locally:
 * Extends general account with password and
 * Supervised/Supervisor accounts.
 * etc.
 * <p>
 * Also stores the data locally.
 * <p>
 * Singleton, lives in BMSApplication.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.ArrayList;

public class UserAccount extends Account {
    private String password = "";
    private ArrayList<Account> supervisors;
    private ArrayList<Account> supervisees;
    public static final String USER_ID_KEY = "UserID";
    public static final String USER_NAME_KEY = "UserName";
    public static final String USER_EMAIL_KEY = "UserEmail";
    public static final String USER_PASSWORD_KEY = "UserPassword";

    // you can call this with ActivityName.this
    // if the return is null then show the login page
    public UserAccount getUserAccount(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                "UserAccount", Context.MODE_PRIVATE);
        if (sharedPref.contains(USER_ID_KEY)) {
            int userID = sharedPref.getInt(USER_ID_KEY, 0);
            String userName = sharedPref.getString(USER_NAME_KEY, "");
            String userEmail = sharedPref.getString(USER_PASSWORD_KEY, "");
            String password = sharedPref.getString(USER_PASSWORD_KEY, "");
            return new UserAccount(userID, userName, userEmail, password);
        } else
            return null;
    }

    public UserAccount(int userID, String userName, String userEmail, String password) {
        super(userID, userName, userEmail);
        this.password = password;
        supervisors = new ArrayList<>();
        supervisees = new ArrayList<>();
    }


    public String getPassword() {
        return password;
    }

    // save account to internal storage using shearedpreferences
    // you need to call it manually
    public void saveToFile(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                "UserAccount", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(USER_ID_KEY, getUserID());
        editor.putString(USER_NAME_KEY, getUserName());
        editor.putString(USER_EMAIL_KEY, getUserEmail());
        editor.putString(USER_PASSWORD_KEY, getPassword());
        editor.apply();
        Toast.makeText(context, "Account saved.", Toast.LENGTH_LONG).show();
    }

    // call at logout
    public void removeSavedFile(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                "UserAccount", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
        Toast.makeText(context, "Account removed.", Toast.LENGTH_LONG).show();
    }

    // call to access, add or remove  Supervisors
    public ArrayList<Account> getSupervisors() {
        return supervisors;
    }

    // call to access, add or remove Supervisees
    public ArrayList<Account> getSupervisees() {
        return supervisees;
    }


}
