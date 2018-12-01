package com.example.durandal.budgetingmadesimple;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.android.gms.common.util.ArrayUtils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.Instant;
import java.util.Arrays;



import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

//import org.apache.commons.lang3.ArrayUtils;


/**
 *  NOTES:
 *  Spinner is the android name for a dropdown menu.
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Database bmsDb;
    private final String[] categoryDropdownDefault = {ExpenditureSystem.ALL_CATEGORY};

    protected static List<MainListView> mainList;
    private ExpenditureArrayAdapter adapter;
    private ListView expList;
    private boolean checked;
    private Spinner timeDropdown;
    private Spinner catDropdown;
    protected static FloatingActionButton fab;
    protected static FloatingActionButton delFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Set up list of expenditures.
        expList = (ListView) findViewById(R.id.expList);


        Log.e("Debug: ","LENGTH:" + BMSApplication.expSystem.getExpendituresAll().toArray().length);
        /*final ArrayAdapter adapter = new ArrayAdapter(this, R.layout.simple_row,
                BMSApplication.expSystem.getExpendituresAll().toArray() );*/



        fab = (FloatingActionButton)findViewById(R.id.addExpenditureButton);

        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, addExpenditurePrompt.class));

            }
        });

        delFab = (FloatingActionButton) findViewById(R.id.deleteExpenditureBurron);

        delFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int[] positions = new int[mainList.size()];
                for (int i = 0; i < MainActivity.mainList.size(); i++) {
                    if (mainList.get(i).isChecked()) {
                        positions[i] = 1;
                    }
                    else {
                        positions[i] = 0;
                    }
                }
                Intent in = new Intent(MainActivity.this, delExpenditurePrompt.class);
                in.putExtra("Positions",positions);
                startActivity(in);

            }
        });

        Object[] expenArray = BMSApplication.expSystem.getExpendituresAll().toArray();
        mainList = new LinkedList<>();
        for (int i = 0; i < expenArray.length; i++) {
            mainList.add(new MainListView((Expenditure)expenArray[i]));
        }

        adapter = new ExpenditureArrayAdapter(this, mainList);
        expList.setAdapter(adapter);
        //expList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        /*
        expList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3)
            {

                MainListView expenView = adapter.getItem(position);
                expenView.toggleChecked();
                Log.d("Clicked: ", expenView.getName());
                MainListViewHolder viewHolder = (MainListViewHolder)view.getTag();
                if (viewHolder == null) {
                    return;
                }
                viewHolder.getCheckBox().setChecked(expenView.isChecked());
                if (MainListView.hasSelected(mainList)) {
                    fab.hide();
                }


            }

        }); */

        //BMSApplication.expSystem.addCategory(false,0,"food");

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
            mainList = new LinkedList<MainListView>();
            LinkedList<Expenditure> array = BMSApplication.expSystem.getExpendituresByCategory(catSelection);
            for (int i = 0; i < array.size(); i++) {
                mainList.add(new MainListView(array.get(i)));
            }
            adapter = new ExpenditureArrayAdapter(this, mainList);
        }
        else if(timeSelection.equals("last 7 days")) {
            mainList = new LinkedList<MainListView>();
            LinkedList<Expenditure> array = BMSApplication.expSystem.getExpendituresTimeAndCat(
                    now.minusDays( 7 ), now, catSelection);
            for (int i = 0; i < array.size(); i++) {
                mainList.add(new MainListView(array.get(i)));
            }
            adapter = new ExpenditureArrayAdapter(this, mainList);
            /*
            adapter =  new ArrayAdapter(this, R.layout.simple_row, R.id.label,
                    BMSApplication.expSystem.getExpendituresTimeAndCat(
                            now.minusDays( 7 ), now, catSelection));
            */
        }
        else if(timeSelection.equals("this month")) {
            mainList = new LinkedList<MainListView>();
            LinkedList<Expenditure> array =  BMSApplication.expSystem.getExpendituresTimeAndCat(
                    now.minusDays(now.getDayOfMonth()),
                    now,
                    catSelection);
            for (int i = 0; i < array.size(); i++) {
                mainList.add(new MainListView(array.get(i)));
            }
            adapter = new ExpenditureArrayAdapter(this, mainList);
            /*
            adapter =  new ArrayAdapter(this, R.layout.simple_row, R.id.label,
                    BMSApplication.expSystem.getExpendituresTimeAndCat(
                            now.minusDays(now.getDayOfMonth()),
                            now,
                            catSelection));
                            */
        }

        // Reset expenditures.
        target.setAdapter(adapter);

          // Temporary
    }


    /**
     * Showcase how to interact with the SQL database
     */
    private void showcaseDatabase() {
        // Create SQL database
        bmsDb = new Database(this);
        showcaseDatabaseUserTable(bmsDb);
        showcaseDatabaseExpenditureTable(bmsDb);
    }

    /**
     * Showcase how to interact with user data in the SQL database
     * @param bmsDb
     */
    private void showcaseDatabaseUserTable(Database bmsDb) {
        // Create user
        long isCreated = bmsDb.createUser("FakeUser", "fakepass",
                "fake@gmail.com", "Are you fake?", "Yes",
                100, 50, 50);
        if (isCreated != -1)
            Log.d("SQL Database Testing", "User inserted");
        else
            Log.d("SQL Database Testing", "User not inserted");

        // Retrieve user data
        Cursor res = bmsDb.getUser("FakeUser");
        if (res.getCount() == 0)
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

    /**
     * Showcase how to interact with expenditure data in the SQL database
     * @param bmsDb
     */
    private void showcaseDatabaseExpenditureTable(Database bmsDb) {
        // Create expenditure
        long isCreated = bmsDb.createExpenditure(1, 1, 100,
                Long.toString(Instant.now().getEpochSecond()), false);
        if(isCreated != -1)
            Log.d("SQL Database Testing","Expenditure inserted");
        else
            Log.d("SQL Database Testing","Expenditure not inserted");

        // Retrieve expenditure data
        Cursor res = bmsDb.getExpenditures("FakeUser");
        if(res.getCount() == 0)
            Log.d("SQL Database Testing", "No expenditure data returned");
        else
            while (res.moveToNext()) {
                Log.d("SQL Database Testing", "ExpenditureId: " + res.getString(0));
                Log.d("SQL Database Testing", "UserId: " + res.getString(1));
                Log.d("SQL Database Testing", "CategoryId: " + res.getString(2));
                Log.d("SQL Database Testing", "amount: " + res.getString(3));
                Log.d("SQL Database Testing", "date: " + res.getString(4));
                Log.d("SQL Database Testing", "isRecurring: " + res.getString(5));
                Log.d("SQL Database Testing", "secretQuestionAnswer: " + res.getString(6));
            }
    }
}
