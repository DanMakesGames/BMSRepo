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
import android.database.Cursor;
import android.widget.Toast;

import java.util.ArrayList;

public class UserAccount extends Account {
    private String password = "";
    private ArrayList<LinkedAccount> supervisors;
    private ArrayList<LinkedAccount> supervisees;
    private static final String USER_ID_KEY = "UserID";
    private static final String USER_NAME_KEY = "UserName";
    private static final String USER_EMAIL_KEY = "UserEmail";
    private static final String USER_PASSWORD_KEY = "UserPassword";


    // you can call this with ActivityName.this
    // if the return is null then show the login page
    public static UserAccount getUserAccount(Context context) {
        if (BMSApplication.account == null) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    "UserAccount", Context.MODE_PRIVATE);
            if (sharedPref.contains(USER_ID_KEY)) {
                int userID = sharedPref.getInt(USER_ID_KEY, 0);
                String userName = sharedPref.getString(USER_NAME_KEY, "");
                String userEmail = sharedPref.getString(USER_PASSWORD_KEY, "");
                String password = sharedPref.getString(USER_PASSWORD_KEY, "");
                BMSApplication.account = new UserAccount(userID, userName, userEmail, password);
            } else
                BMSApplication.account = null;
        }

        return BMSApplication.account;
    }

    public static void updateUserAccount(int userID, String userName, String userEmail, String password, Context context) {
        BMSApplication.account = new UserAccount(userID, userName, userEmail, password);
        BMSApplication.account.saveToFile(context);
    }

    public static void logout(Context context) {
        BMSApplication.account.removeSavedFile(context);
        BMSApplication.account = null;
    }

    private UserAccount(int userID, String userName, String userEmail, String password) {
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
    private void saveToFile(Context context) {
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
    private void removeSavedFile(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                "UserAccount", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
        Toast.makeText(context, "Account removed.", Toast.LENGTH_LONG).show();
    }

    // call to access Supervisors
    public ArrayList<LinkedAccount> getSupervisors() {
        if (supervisors == null) {
            Cursor supervisorCursor = BMSApplication.database.getSupervisors(getUserID());
            Cursor userCursor;
            int supervisorId;
            int linkStatus;
            String supervisorName;
            String supervisorEmail;
            while (supervisorCursor.moveToNext()) {
                supervisorId = supervisorCursor.getInt(1);
                //TODO get link status, and get user information with ID. following needs to be changed
                linkStatus = supervisorCursor.getInt(0);
                //find in user table by id
                userCursor = BMSApplication.database.getUser("" + supervisorId);
                supervisorName = userCursor.getString(1);
                supervisorEmail = userCursor.getString(3);
                supervisors.add(new LinkedAccount(supervisorId, supervisorName, supervisorEmail, linkStatus));
            }
        }
        return supervisors;
    }

    // call to access Supervisees
    public ArrayList<LinkedAccount> getSupervisees() {
        if (supervisees == null) {
            Cursor supervisorCursor = BMSApplication.database.getSupervisees(getUserID());
            Cursor userCursor;
            int superviseeId;
            int linkStatus;
            String superviseeName;
            String superviseeEmail;
            while (supervisorCursor.moveToNext()) {
                superviseeId = supervisorCursor.getInt(1);
                //TODO get link status, and get user information with ID. following needs to be changed
                linkStatus = supervisorCursor.getInt(0);
                //find in user table by id
                userCursor = BMSApplication.database.getUser("" + superviseeId);
                superviseeName = userCursor.getString(1);
                superviseeEmail = userCursor.getString(3);
                supervisees.add(new LinkedAccount(superviseeId, superviseeName, superviseeEmail, linkStatus));
            }
        }
        return supervisees;
    }

    // means send link request
    // check input before calling
    public boolean addSupervisee(String superviseeEmail) {
        int linkStatus = 0;
        int superviseeId = 0;
        String superviseeName = "";
        //TODO get id etc. from email check if is linked, add status here "0"
        if (BMSApplication.database.createSupervisor(getUserID(), superviseeId)) {
            supervisees.add(new LinkedAccount(superviseeId, superviseeName, superviseeEmail, linkStatus));
            return true;
        } else
            return false;
    }

    //simply remove or accept unlink request
    public boolean removeSupervisee(int superviseeId) {
        //update status both online and in memory
        return true;
    }

    public boolean denyUnlinkRequest(int superviseeId) {
        //update status both online and in memory
        return true;
    }

    //accept link request
    public boolean addSupervisor(int supervisorId) {
        //update status both online and in memory
        return true;
    }

    //send unlink request
    public boolean removeSupervisor(int supervisorId) {
        //update status both online and in memory
        return true;
    }

    //decline link request
    public boolean declineLinkRequest(int supervisorId) {
        return true;
    }


}
