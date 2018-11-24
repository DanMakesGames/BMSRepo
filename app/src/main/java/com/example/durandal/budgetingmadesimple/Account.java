package com.example.durandal.budgetingmadesimple;

/*
 *
 * Stores general account information.
 * Including user id, user name and user email.
 *
 */
public class Account {
    private int userID;
    private String userName;
    private String userEmail;

    public Account(int userID, String userName, String userEmail) {
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

}
