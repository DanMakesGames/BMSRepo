package com.example.durandal.budgetingmadesimple;

import java.time.ZonedDateTime;
import java.util.LinkedList;

/**
 * Class handels the storage, sorting, access, and modification of Expenditures.
 * Must be initialized at app startup either from cache or from the online data base.
 *
 * It's only member object is the data structure holding all the expenditures.
 */
public final class ExpenditureSystem {

    private LinkedList<Expenditure> expenditures;


    /**
     * Does some shit need to go here? Not entriely sure.
     */
    ExpenditureSystem() {

    }


    /**
     * This function should fill the expenditure data structure from the online database. Called
     * durring startup.
     * Not really sure how it should be implemented.
     *
     * Returns true if successful, false if not.
     *
     * unimplemented.
     */
    public boolean populateFromDataBase() {
        return true;
    }


    /**
     * Populates expenditure data structure from the expenditures saved on the device (cached so
     * that the app doesn't have to pull from the database every time start up occurs.)
     *
     * returns true if successful, false if not.
     *
     * unimplemented.
     */
    public boolean populateFromDevice() {
        return true;
    }


    /**
     * One getter of expenditures.
     * @param start
     * @param end
     * @return expenditures in order newest to oldest from start to end.
     */
    public final LinkedList<Expenditure> getExpendituresByDate(ZonedDateTime start, ZonedDateTime end) {
        return null;
    }


    /**
     * Gets list of expenditures in order newest to oldest that are of the same category.
     * @param categoryName
     * @return
     */
    public final LinkedList<Expenditure> getExpendituresByCategory (String categoryName) {
        return null;
    }


    /**
     * Creates a new expenditure. Not only adds it to local dataStructure, but also sends request
     * for it to be added to the DataBase.
     * @param inValue
     * @param inCategory
     * @param inReoccurring
     * @param inRate
     * @return true is successful, false if not.
     */
    public boolean addExpenditure(float inValue, String inCategory, boolean inReoccurring, ReoccurringRate inRate) {
        return true;

    }

    /**
     * Deletes the expenditure. Not only from the local data structure, but also from the database
     *
     * @param inExpenditure
     * @return true is successful, false if not.
     */
    public boolean deleteExpenditure(Expenditure inExpenditure) {
        return true;
    }



}
