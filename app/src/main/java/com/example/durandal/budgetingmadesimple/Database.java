package com.example.durandal.budgetingmadesimple;


import android.provider.ContactsContract;

import java.time.Instant;

/**
 * This is an intermediatary level between firebase and the
 */
public class Database {

    // Add all member fields that are needed for proper interaction with firebase.

    public Database() {

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
