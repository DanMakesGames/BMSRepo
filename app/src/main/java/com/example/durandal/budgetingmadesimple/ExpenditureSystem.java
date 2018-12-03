package com.example.durandal.budgetingmadesimple;


import android.annotation.TargetApi;
import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import java.time.ZonedDateTime;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Class handels the storage, sorting, access, and modification of Expenditures.
 * Must be initialized at app startup either from cache or from the online data base.
 *
 * It's only member object is the data structure holding all the expenditures.
 */
public final class ExpenditureSystem {

    private LinkedList<Expenditure> expenditures;
    private HashMap<String,Category> categories;
    public final static String ALL_CATEGORY = "all categories";
    public final static String SEL_CATEGORY = "Select a category";


    /**
     * Does some shit need to go here? Not entriely sure.
     */
    ExpenditureSystem() {
        expenditures = new LinkedList<Expenditure>();
        categories = new HashMap<String, Category>();
    }


    /**
     * This function should fill the expenditure data structure and categories data structure from
     * the online database. Called during startup.
     *
     * Returns true if successful, false if not.
     */
    public boolean populateFromDatabase(String username) {

        // Populate categories first.
        // Get categories, populate categories list, populate local hasMap
        Cursor catCursor = BMSApplication.database.getCategories(username);

        // Hash map used for linking IDs to category names. Used in expenditure parsing process.
        HashMap<Integer, String> IdToName = new HashMap<Integer, String>();

        // loop parsing categories and adding them to Category hashmap.
        while(catCursor.moveToNext()) {

            // Extract category parameters from database entry.
            int catId = Integer.parseInt(catCursor.getString(0));
            float budget = Float.parseFloat(catCursor.getString(3));
            String name = catCursor.getString(2);

            // emplace new category into app Category storage
            categories.put(name, new Category(budget != 0,budget,name, catId));
            IdToName.put(catId,name);
        }

        Log.d("populate", IdToName.toString());

        // Now lets populate the Expenditures.
        Cursor expCursor = BMSApplication.database.getExpenditures(username);


        // no expenditures in database to parse.
        if(expCursor.getCount() == 0)
            return false;

        // loop through, turning exp database items into Expenditure objects, and adding them to
        // the linked list.
        while(expCursor.moveToNext()) {

            // extract expenditure parameters
            float value = Float.parseFloat(expCursor.getString(3));
            Instant timestamp = Instant.ofEpochSecond(Long.parseLong(expCursor.getString(4)));
            String category = IdToName.get( Integer.parseInt(expCursor.getString(2)) );
            int Id = Integer.parseInt(expCursor.getString(0));

            Log.d("populate", value +", catId: "+expCursor.getString(2));
            // create new expenditure object.
            Expenditure newExp = new Expenditure(
                        value,      //value
                        category,   //category
                        Id,
                        timestamp); //timestamp

            // add to expenditure list.
            expenditures.addFirst(newExp);
        }

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
     * Returns all of the names of the categories in an array format.
     * @return
     */
    public String[] getCategoryNames() {
        String[] stringArray = {};
        return categories.keySet().toArray( stringArray);
    }

    /**
     * Unfiltered immutable get of expenditures.
     * @return
     */
    public final LinkedList<Expenditure> getExpendituresAll() {
        return expenditures;
    }

    /**
     * One getter of expenditures.
     * @param start all times after this time (older)
     * @param end all times before this time (more recent)
     * @return expenditures in order newest to oldest from start to end.
     */

    public final LinkedList<Expenditure> getExpendituresByDate(ZonedDateTime start, ZonedDateTime end) {

        LinkedList<Expenditure> return_list = new LinkedList<>();
        Iterator expenditures_it = expenditures.iterator();
        Instant start_instant = start.toInstant();
        Instant end_instant = end.toInstant();
        while(expenditures_it.hasNext()) {
            Expenditure exp = (Expenditure)expenditures_it.next();
            Instant time = exp.getTimeStamp();

            // removing time.isBefore is the only way i COUld get this shit to work
            if(time.isAfter(start_instant) /*&& time.isBefore(end_instant) */)
                return_list.add(exp);
        }
        return return_list;
    }


    /**
     * Gets list of expenditures in order newest to oldest that are of the same category. If you
     * enter ALL_CATEGORY, does not sort and returns all.
     * @param categoryName
     * @return
     */
    public final LinkedList<Expenditure> getExpendituresByCategory (String categoryName) {

        // return all expenditures.
        if(categoryName.equals(ALL_CATEGORY)) {
            return expenditures;
        }

        LinkedList<Expenditure> return_list = new LinkedList<Expenditure>();
        Iterator expenditures_it = expenditures.iterator();
        while(expenditures_it.hasNext()) {
            Expenditure  exp = (Expenditure)expenditures_it.next();
            if(exp.getCategory().equals(categoryName)) {
                return_list.add(exp);
            }
        }
        return return_list;

    }

    /**
     * Return Expenditures sorted by date and time.
     * @param categoryName
     * @param start
     * @param end
     * @return
     */
    public final LinkedList<Expenditure> getExpendituresTimeAndCat ( ZonedDateTime start, ZonedDateTime end, String categoryName) {

        if (categoryName == null)
            return null;
        
        LinkedList<Expenditure> dateExps = getExpendituresByDate(start, end);
        LinkedList<Expenditure> return_list = new LinkedList<Expenditure>();
        Iterator expenditures_it = dateExps.iterator();

        if (categoryName == null) {
            return return_list;
        }

        if (categoryName.equals(ALL_CATEGORY))
            return dateExps;
        while(expenditures_it.hasNext()) {
            Expenditure exp = (Expenditure)expenditures_it.next();

            if(exp.getCategory().equals(categoryName))
                return_list.add(exp);
        }



        return return_list;
    }


    /**
     * Creates a new expenditure. Not only adds it to local dataStructure, but also sends request
     * for it to be added to the DataBase.
     * @param inValue
     * @param inCategory
     * @param inReoccurring
     * @param inRate
     * @return true is successful, false if not.
     *
     */
    public boolean addExpenditure(float inValue, String inCategory, boolean inReoccurring, ReoccurringRate inRate) {

        Log.e("addExpenditure", "adding");

        Instant stamp = Instant.now();

        long expId = BMSApplication.database.createExpenditure(
                BMSApplication.account.getUserID(),
                categories.get(inCategory).getCategoryId(),
                inValue,
                Long.toString(stamp.getEpochSecond()),
                inReoccurring);

        if (expId != -1) {

            Expenditure newExpen = new Expenditure(inValue,inCategory, (int) expId, stamp);

            expenditures.addFirst(newExpen);
            return true;
        }

        return false;
    }


    /**
     * Creates a new expenditure. Not only adds it to local dataStructure, but also sends request
     * for it to be added to the DataBase.
     * @param expen
     * @return true is successful, false if not.
     */
    /*
    public boolean addExpenditure(Expenditure expen) {

        if (BMSApplication.database.createExpenditure() ) {
            expenditures.addFirst(expen);
            return true;
        }
        return false;

    }
*/
    /**
     * Sepcial Add function that allows date to be set manually. Used for debug.
     * @param inValue
     * @param inCategory
     * @param time

     * @return
     *
     */
    public boolean addExpDEBUG(float inValue, String inCategory, Instant time) {


        int expId = (int) BMSApplication.database.createExpenditure(BMSApplication.account.getUserID(),
                categories.get(inCategory).getCategoryId(), inValue,
                Long.toString(time.getEpochSecond()),
                false);

        // if creation was successful.
        if (expId != -1) {

            Expenditure newExpen = new Expenditure(inValue, inCategory, expId, time);

            expenditures.addFirst(newExpen);
            return true;
        }
        return false;

    }

    /**
     * Deletes the expenditure. Not only from the local data structure, but also from the database
     *
     * @param inExpenditure
     * @return true is successful, false if not.
     *
     *
     */
    public boolean deleteExpenditure(Expenditure inExpenditure) {


        if (!BMSApplication.database.deleteExpenditure(inExpenditure.getExpId())) {
            return false;
        }
        for (int i = 0; i < expenditures.size(); i++) {
            if (expenditures.get(i).equals(inExpenditure)) {
                expenditures.remove(i);
                return true;
            }
        }
        return false;
    }


    /**
     * Creates and adds an new category to the list of categories. Needs to check
     * @param bIsBudgeted
     * @param budget
     * @param name
     * @return true if added. False if a category with that name already existed and thus the category was not added.
     *
     */
    public boolean addCategory(boolean bIsBudgeted, float budget, String name) {

        // check if key already in hashtable
        if( categories.containsKey(name) )
            return false;



        // insert onto database
        int catId  = (int) BMSApplication.database.createExpCategory(BMSApplication.account.getUserID(),name,budget);

        // only add locally if cat add
        if(catId != -1) {
            // insert
            categories.put(name, new Category(bIsBudgeted, budget, name, catId));
            return true;
        }

        // data base request was not successful.
        return false;
    }

    /**
     * Finds category if it exists in the app's storage and returns it's reference.
     * @param name
     * @return the category object or NULL if not found.
     */
    public Category getCategory(String name){
        return categories.get(name);
    }


}
