package com.example.durandal.budgetingmadesimple;

import android.database.Cursor;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import java.lang.Object;
//import org.apache.commons.lang3.ArrayUtils;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Arrays;
import java.time.ZonedDateTime;


/**
 *  NOTES:
 *  Spinner is the android name for a dropdown menu.
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Database bmsDb;
    private final String[] categoryDropdownDefault = {ExpenditureSystem.ALL_CATEGORY};


    private ListView expList;
    private Spinner timeDropdown;
    private Spinner catDropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testDatabase();

        //Test Expenditure adding and filtering.

        BMSApplication.expSystem.addExpenditure(100,"food", false, ReoccurringRate.NONE);
        /*
        BMSApplication.expSystem.addExpenditure(600,"video games", false, ReoccurringRate.NONE);
        BMSApplication.expSystem.addExpDEBUG(0, "food", ZonedDateTime.now());
        BMSApplication.expSystem.addExpDEBUG(10, "food", ZonedDateTime.now().minusDays(1));
        BMSApplication.expSystem.addExpDEBUG(20, "food", ZonedDateTime.now().minusDays(2));
        BMSApplication.expSystem.addExpDEBUG(30, "food", ZonedDateTime.now().minusDays(3));
        BMSApplication.expSystem.addExpDEBUG(40, "food", ZonedDateTime.now().minusDays(4));
        BMSApplication.expSystem.addExpDEBUG(50, "food", ZonedDateTime.now().minusDays(5));
        BMSApplication.expSystem.addExpDEBUG(60, "food", ZonedDateTime.now().minusDays(6));
        BMSApplication.expSystem.addExpDEBUG(70, "food", ZonedDateTime.now().minusDays(7));
        BMSApplication.expSystem.addExpDEBUG(80, "food", ZonedDateTime.now().minusDays(8));
        BMSApplication.expSystem.addExpDEBUG(1000, "video games", ZonedDateTime.now().minusDays(100));
        BMSApplication.expSystem.addExpDEBUG(80085, "food", ZonedDateTime.now().minusDays(100));
        */
        BMSApplication.expSystem.addCategory(false,0,"food");
        BMSApplication.expSystem.addCategory(false,0,"video games");


        // Set up list of expenditures.
        expList = (ListView) findViewById(R.id.expList);

        Log.e("Debug: ","LENGTH:" + BMSApplication.expSystem.getExpendituresAll().toArray().length);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.activity_listview,
                BMSApplication.expSystem.getExpendituresAll().toArray() );

        expList.setAdapter(adapter);


        // Set up time dropdown
        timeDropdown = (Spinner) findViewById(R.id.time_dropdown);
        ArrayAdapter timeDropAdapter = ArrayAdapter.createFromResource(this,
                R.array.time_array, R.layout.our_spinner_item);

        timeDropAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        timeDropdown.setAdapter(timeDropAdapter);
        timeDropdown.setSelection(0);
        timeDropdown.setOnItemSelectedListener(this);

        // Set up category drop down
        catDropdown = (Spinner) findViewById(R.id.category_dropdown);

        ArrayAdapter catAdapter = new ArrayAdapter(this,R.layout.our_spinner_item,
                ArrayUtils.concat(categoryDropdownDefault,
                        BMSApplication.expSystem.getCategoryNames()));

        catAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        catDropdown.setAdapter(catAdapter);
        catDropdown.setSelection(0);
        catDropdown.setOnItemSelectedListener(this);



        //loop adding
    }

    // Used by drop downs
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {



        // determine which drop down was being used.
        switch(parent.getId()){
            case R.id.category_dropdown:
                filterExpenditures(expList, timeDropdown, catDropdown);
                break;
            case R.id.time_dropdown:
                filterExpenditures(expList, timeDropdown, catDropdown);
                break;
        }

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void filterExpenditures(ListView target, Spinner timeDD, Spinner catDD) {
        final String allTime = "all times";
        // get time
        ZonedDateTime now = ZonedDateTime.now();
        ArrayAdapter adapter = null;


        String timeSelection = (String) timeDD.getSelectedItem();
        String catSelection = (String) catDD.getSelectedItem();

        // Filter by time.
        if(timeSelection.equals("all times")) {
            adapter = new ArrayAdapter(this, R.layout.activity_listview,
                    BMSApplication.expSystem.getExpendituresByCategory(catSelection));
        }
        else if(timeSelection.equals("last 7 days")) {
            adapter =  new ArrayAdapter(this, R.layout.activity_listview,
                    BMSApplication.expSystem.getExpendituresTimeAndCat(
                            now.minusDays( 7 ), now, catSelection));

        }
        else if(timeSelection.equals("this month")) {
            adapter =  new ArrayAdapter(this, R.layout.activity_listview,
                    BMSApplication.expSystem.getExpendituresTimeAndCat(
                            now.minusDays(now.getDayOfMonth()),
                            now,
                            catSelection));
        }

        // Reset expenditures.
        target.setAdapter(adapter);

          // Temporary
    }

    private void testDatabase() {
        // Create SQL database
        bmsDb = new Database(this);

        // Create user
        boolean isCreated = bmsDb.createUser("FakeUser", "fakepass",
                "fake@gmail.com", "Are you fake?", "Yes",
                100, 50, 50);
        if(isCreated == true)
            Log.d("SQL Database Testing","Inserted");
        else
            Log.d("SQL Database Testing","Not inserted");

        // Retrieve data
        Cursor res = bmsDb.getUser("FakeUser");
        if(res.getCount() == 0)
            Log.d("SQL Database Testing", "No data returned");
        else
            while (res.moveToNext()) {
                Log.d("SQL Database Testing", "UserId: " + res.getString(0));
                Log.d("SQL Database Testing", "Username: " + res.getString(1));
                Log.d("SQL Database Testing", "Password: " + res.getString(2));
                Log.d("SQL Database Testing", "email: " + res.getString(3));
                Log.d("SQL Database Testing", "secretQuestion: " + res.getString(4));
                Log.d("SQL Database Testing", "secretQuestionAnswer: " + res.getString(5));
                Log.d("SQL Database Testing", "budget: " + res.getString(6));
                Log.d("SQL Database Testing", "savingsGoal: " + res.getString(7));
                Log.d("SQL Database Testing", "bankBalance: " + res.getString(8));
            }


    }
}
