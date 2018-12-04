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
        supervisors = new ArrayList<>();
            Cursor supervisorCursor = BMSApplication.database.getSupervisors(getUserID());
            Cursor userCursor;
            int supervisorId;
            int linkStatus;
            int relationId;
            String supervisorName;
            String supervisorEmail;

            while (supervisorCursor.moveToNext()) {
                //get supervisor table
                relationId = supervisorCursor.getInt(0);
                supervisorId = supervisorCursor.getInt(1);
                linkStatus = supervisorCursor.getInt(3);
                //find in user table by id
                userCursor = BMSApplication.database.getUser(supervisorId);
                userCursor.moveToNext();
                supervisorName = userCursor.getString(1);
                supervisorEmail = userCursor.getString(3);
                supervisors.add(new LinkedAccount(supervisorId, supervisorName, supervisorEmail, linkStatus, relationId));
            }
        return supervisors;
    }

    // call to access Supervisees
    public ArrayList<LinkedAccount> getSupervisees() {
        supervisees = new ArrayList<>();

            Cursor supervisorCursor = BMSApplication.database.getSupervisees(getUserID());
            Cursor userCursor;
            int superviseeId;
            int linkStatus;
            int relationId;
            String superviseeName;
            String superviseeEmail;
            while (supervisorCursor.moveToNext()) {
                //get supervisor table
                relationId = supervisorCursor.getInt(0);
                superviseeId = supervisorCursor.getInt(2);
                linkStatus = supervisorCursor.getInt(3);
                //find in user table by id
                userCursor = BMSApplication.database.getUser(superviseeId);
                userCursor.moveToNext();
                superviseeName = userCursor.getString(1);
                superviseeEmail = userCursor.getString(3);
                supervisees.add(new LinkedAccount(superviseeId, superviseeName, superviseeEmail, linkStatus, relationId));
            }

        return supervisees;
    }

    // call to access Linked Supervisees
    public ArrayList<LinkedAccount> getLinkedSupervisees() {
        supervisees = getSupervisees();
        ArrayList<LinkedAccount> linkedSupervisees = new ArrayList<LinkedAccount>();
        for (int i = 0; i < supervisees.size(); i++) {
            if (supervisees.get(i).isLinked()) {
                linkedSupervisees.add(supervisees.get(i));
            }
        }
        return linkedSupervisees;
    }

    // means send link request
    // check input before calling
    // if return false prompt user no account or already linked
    public int addSupervisee(String superviseeName) {
        int linkStatus = 0;
        int superviseeId = 0;
        int relationId = 0;
        int superviseeIdInTable;
        LinkedAccount supervisee;
        String superviseeEmail = "";

        // get user by name
        Cursor userCursor = BMSApplication.database.getUser(superviseeName);
        if(userCursor.getCount() == 0){
            return 1; //no account
        }
        userCursor.moveToNext();
        superviseeId = userCursor.getInt(0);
        superviseeEmail = userCursor.getString(3);

        //find in all supervisee
        Cursor superviseeCursor = BMSApplication.database.getSupervisees(getUserID());
        while (superviseeCursor.moveToNext()) {
            superviseeIdInTable = superviseeCursor.getInt(2);
            if(superviseeId == superviseeIdInTable){
                //found supervisee, check their status
                linkStatus = superviseeCursor.getInt(3);
                relationId = superviseeCursor.getInt(0);
                //create new supervisee object
                supervisee = new LinkedAccount(superviseeId,superviseeName,superviseeEmail,linkStatus, relationId);
                //check if they are linked
                if(supervisee.isLinked()||linkStatus==LinkedAccount.REQUEST_SENT){
                    return 2;
                }else{
                    //have record but not linked, then send request
                    BMSApplication.database.updateSupervisor(relationId,getUserID(),superviseeId,LinkedAccount.REQUEST_SENT);
                    //refresh local list
                    supervisees = null;
                    getSupervisees();
                    return 0;
                }
            }
        }

        // if supervisee is not found
        relationId = (int)BMSApplication.database.createSupervisor(getUserID(),superviseeId,LinkedAccount.REQUEST_SENT);
        if (relationId<0){
            return 3;
        }
        supervisees.add(new LinkedAccount(superviseeId,superviseeName,superviseeEmail,LinkedAccount.REQUEST_SENT, relationId));
        return -1;

    }

    //simply remove or accept unlink request, call by supervisor
    public boolean removeSupervisee(int superviseeId) {
        //update status both online and in memory
        return updateSuperviseeStatus(superviseeId,LinkedAccount.UNLINK_GRANTED);
    }
    // call by supervisor
    public boolean denyUnlinkRequest(int superviseeId) {
        //update status both online and in memory
        return updateSuperviseeStatus(superviseeId,LinkedAccount.UNLINK_DENIED);
    }

    //accept link request, call by supervisee
    public boolean addSupervisor(int supervisorId) {
        //update status both online and in memory
        return updateSupervisorStatus(supervisorId,LinkedAccount.ACCEPTED);
    }

    //send unlink request, call by supervisee
    public boolean removeSupervisor(int supervisorId) {
        //update status both online and in memory
        return updateSupervisorStatus(supervisorId,LinkedAccount.UNLINK_SENT);
    }

    //decline link request, call by supervisee
    public boolean declineLinkRequest(int supervisorId) {
        return updateSupervisorStatus(supervisorId,LinkedAccount.DECLINED);
    }

    private boolean updateSuperviseeStatus(int superviseeId,int status ){
        int index = 0;
        int relationId = 0;
        for (LinkedAccount supervisee:supervisees) {
            if(supervisee.getUserID() == superviseeId){
                relationId = supervisee.getRelationId();
                index = supervisees.indexOf(supervisee);
                break;
            }
        }
        BMSApplication.database.updateSupervisor(relationId,getUserID(),superviseeId,status);
        supervisees.get(index).setStatus(status);
        return true;
    }

    private boolean updateSupervisorStatus(int supervisorId,int status ){
        int index = 0;
        int relationId = 0;
        for (LinkedAccount supervisor:supervisors) {
            if(supervisor.getUserID() == supervisorId){
                relationId = supervisor.getRelationId();
                index = supervisors.indexOf(supervisor);
                break;
            }
        }
        BMSApplication.database.updateSupervisor(relationId,supervisorId,getUserID(),status);
        supervisors.get(index).setStatus(status);
        return true;
    }


}
