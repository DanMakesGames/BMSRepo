package com.example.durandal.budgetingmadesimple;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.LinkedList;

enum ReoccurringRate {
    NONE, WEEKLY, MONTHLY;
}

public class Expenditure {

    // used by the database for modifying and deleteing.
    private int expId;
    // Controls rate of reoccurrance.
    private final ReoccurringRate rate;
    // Time of creation for the expenditure. Most useful as a proper ID for the expenditure, seeing
    // as you can't make two expenditures on the exact nano second.
    private final Instant timeStamp;
    // Monetary value of the expenditure. This is the Money ($) spent.
    private final float value;
    // String for category. Remember, this string can be used to get the Category object entry from
    // the category data structure.
    private String category;
    // Controls if this expenditure is reoccurring.
    private final boolean bIsReoccurring;


    public void setExpId(int expId) {
        this.expId = expId;
    }


    public int getExpId() {
        return expId;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }


    public float getValue() {
        return value;
    }


    public String getCategory() {
        return category;
    }


    /*
    * This needs to be updated so that the database is updated with the change */
    public void setCategory(String category) {
        this.category = category;
    }


    public boolean isReocurring() {
        return bIsReoccurring;
    }


    public ReoccurringRate getRate() {
        return rate;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    Expenditure(float inValue, String inCategory, int Id) {

        timeStamp = Instant.now();
        value = inValue;
        category = inCategory;
        bIsReoccurring = false;
        rate = ReoccurringRate.NONE;

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    Expenditure(float inValue, String inCategory,  int Id, Instant time) {

        timeStamp = time;
        value = inValue;
        category = inCategory;
        bIsReoccurring = false;
        rate = ReoccurringRate.NONE;

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    Expenditure(float inValue, String inCategory, int Id, boolean inReoccurring, ReoccurringRate inRate) {

        timeStamp = Instant.now();
        value = inValue;
        category = inCategory;
        bIsReoccurring = inReoccurring;
        rate = inRate;

    }

    public boolean equals(Expenditure expen) {
        if (expen.bIsReoccurring == this.bIsReoccurring &&
                expen.category == this.category &&
                expen.rate == this.rate &&
                expen.timeStamp == this.timeStamp &&
                expen.value == this.value) {
            return true;
        }
        return false;
    }


    @Override
    public String toString() {
        return "Category: " + category + "\tValue: " + value ;

    }
}
