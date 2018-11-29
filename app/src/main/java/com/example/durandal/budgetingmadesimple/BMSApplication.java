package com.example.durandal.budgetingmadesimple;

import android.app.Application;

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

    @Override
    public void onCreate() {

        super.onCreate();
        singleton = this;

        BMSApplication.database = new Database(getApplicationContext());

        BMSApplication.expSystem.addExpenditure(100,"food", false, ReoccurringRate.NONE);

        BMSApplication.expSystem.addExpenditure(600,"video games", false, ReoccurringRate.NONE);
        BMSApplication.expSystem.addExpDEBUG(0, "food", ZonedDateTime.now());
        BMSApplication.expSystem.addExpDEBUG(10, "food", ZonedDateTime.now().minusDays(1));
        BMSApplication.expSystem.addExpDEBUG(20, "food", ZonedDateTime.now().minusDays(2));
        BMSApplication.expSystem.addExpDEBUG(30, "food", ZonedDateTime.now().minusDays(3));
        BMSApplication.expSystem.addExpDEBUG(40, "food", ZonedDateTime.now().minusDays(4));
        BMSApplication.expSystem.addExpDEBUG(50, "food", ZonedDateTime.now().minusDays(5));
        BMSApplication.expSystem.addExpDEBUG(60, "food", ZonedDateTime.now().minusDays(6));
        BMSApplication.expSystem.addExpDEBUG(70, "food", ZonedDateTime.now().minusDays(7));
        //BMSApplication.expSystem.addExpDEBUG(80, "food", ZonedDateTime.now().minusDays(8));
        //BMSApplication.expSystem.addExpDEBUG(1000, "video games", ZonedDateTime.now().minusDays(100));
        //BMSApplication.expSystem.addExpDEBUG(80085, "food", ZonedDateTime.now().minusDays(100));

        BMSApplication.expSystem.addCategory(false,0,"food");
        BMSApplication.expSystem.addCategory(false,0,"video games");

    }
}
