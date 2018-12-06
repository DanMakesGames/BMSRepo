package com.example.durandal.budgetingmadesimple;

import android.app.Application;
import android.database.Cursor;
import android.util.Log;

import java.time.ZonedDateTime;

/**
 * This is the class that handles Activity independant app state. Can store data with global access
 * level. Data lives as long as the app does.
 *
 * This is where ExpenditureSystem's singleton lives.
 * This is where DataBase's singleton lives
 * Account lives here
 *
 * Also contains a function OnCreate that can is called before any activity is created.
 */
public class BMSApplication extends Application {

    public static BMSApplication singleton;

    public static ExpenditureSystem expSystem = new ExpenditureSystem();


    public static Database database = null;

    // null at startup, instance created during login process.
    public static UserAccount account = null;

    public static void  createAccount(String username, String password, String email, String secretQuestion, String secretAnswer) {

        // create account on database.
        BMSApplication.database.createUser(username,password,email,secretQuestion, secretAnswer,0,0,0);

        // now that user is created we need to setup account.
        // get account
        Cursor userCursor = BMSApplication.database.getUser(username);

        // create instantiate account.
        if (userCursor.getCount() == 0)
            Log.d("", "No data returned");
        else{
            while (userCursor.moveToNext()) {

                BMSApplication.account = new UserAccount(
                        Integer.parseInt(userCursor.getString(0)),
                        userCursor.getString(1),
                        userCursor.getString(3),
                        userCursor.getString(2));
            }
        }
    }

    @Override
    public void onCreate() {

        super.onCreate();
        singleton = this;

        BMSApplication.database = new Database(getApplicationContext());

        //expSystem.addCategory(false,0,"food");

    }
}
