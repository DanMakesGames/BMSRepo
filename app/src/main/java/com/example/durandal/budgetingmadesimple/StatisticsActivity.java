package com.example.durandal.budgetingmadesimple;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

public class StatisticsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // The app's text vies display total budget, total spent, and the remaining amount available
    //private TextView totalBudgetView;
    private TextView totalSpentView;
    //private TextView remainingView;

    // The app's dropdowns are populated based on user's categories, it's supervisees, and the
    // times available
    private Spinner timeDropdown;
    private Spinner categoryDropdown;
    private Spinner superviseeDropdown;

    // Select whether to display statistics for a user or for a whole group
    private Button personalButton;
    private Button groupButton;

    private String[] categories; // Stores the categories of the user
    private String[] times; // Stores the times that we check expenditures for
    private String[] users; // Stores the current user and the supervisees
    //private Account[] supervisees;

    private String totalbudget = "Total Budget: N/A"; // Only modified when we check expenditures of the last month
    private String totalSpent; // Get total spent based on category selected and
    private String remaining = "Remaining: N/A"; // Only modified when we check expenditures of the last month
    private String catSelected; // Current category selected
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionbar.setTitle("Statistics");
        final LinearLayout LL1 = findViewById(R.id.linearLayout);
        final LinearLayout LL2 = findViewById(R.id.linearLayout3);
        final LinearLayout LL3 = findViewById(R.id.linearLayout6);

        //TextView drawer_text = (TextView) findViewById(R.id.drawer_header_text);
        //drawer_text.setText(BMSApplication.account.getUserName());

        // Code for navigation drawer
        mDrawerLayout = findViewById(R.id.drawer_layout);

        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                        mDrawerLayout.bringToFront();
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                        mDrawerLayout.bringToFront();
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                        LL1.bringToFront();
                        LL2.bringToFront();
                        LL3.bringToFront();

                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch(menuItem.getItemId()) {
                            case R.id.nav_item_one: // go to expenditures (home) page
                                Intent intent1 = new Intent(StatisticsActivity.this, MainActivity.class);
                                startActivity(intent1);
                                break;
                            case R.id.nav_item_two: // go to categories page
                                Intent intent2 = new Intent(StatisticsActivity.this, CategoriesActivity.class);
                                startActivity(intent2);
                                break;
                            case R.id.nav_item_three: // go to statistics page
                                Intent intent3 = new Intent(StatisticsActivity.this, StatisticsActivity.class);
                                startActivity(intent3);
                                break;
                            case R.id.nav_item_four: // go to settings
                                Intent intent4 = new Intent(StatisticsActivity.this, AccountSettingsActivity.class);
                                startActivity(intent4);
                                break;
                        }
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                }); // end of navigation bar code


        // Buttons
        personalButton = findViewById(R.id.peronal_button);
        groupButton = findViewById(R.id.group_button);

        // Text Boxes
        //totalBudgetView = findViewById(R.id.total_bugdet);
        totalSpentView = findViewById(R.id.total_spent);
        //remainingView = findViewById(R.id.remaining);

        // Dropdowns
        timeDropdown = findViewById(R.id.time_dropdown);
        categoryDropdown = findViewById(R.id.sort_dropdown);
        superviseeDropdown = findViewById(R.id.supervisee_dropdown);

        // Populate the times
        times = new String[]{"Last 7 Days", "Last Month", "Last 3 Months"};

        // Populate categories based on user's categories
        categories = populateCategories(BMSApplication.expSystem.getCategoryNames());

        // Populate the users array with the current user and it's supervisees
        //if (BMSApplication.account.getSupervisees() == null)
            users = new String[]{"Current user"};

        //else
          //  users = populateUsers(BMSApplication.account.getSupervisees());

        //users = populateUsers(BMSApplication.account.getSupervisees());
        //users = new String[]{"Current User"};

        // Populate the time dropdown
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, times);
        timeDropdown.setAdapter(adapter);

        // Populate the categories dropdown
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        categoryDropdown.setAdapter(adapter2);

        // Populate the supervisee dropdown
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, users);
        superviseeDropdown.setAdapter(adapter3);

        // Set listeners
        categoryDropdown.setOnItemSelectedListener(this);
        timeDropdown.setOnItemSelectedListener(this);
        //superviseeDropdown.setOnItemSelectedListener(this);

        // By default, have overall total spent selected
        categoryDropdown.setSelection(0);

        //By default, have last month as the time selected
        timeDropdown.setSelection(1);

        // By default, have the current user as the user selected
        superviseeDropdown.setSelection(0);

        // Set the text views with their default values
        //totalBudgetView.setText(totalbudget);
        //remainingView.setText(remaining);

        LL1.bringToFront();
        LL2.bringToFront();
        LL3.bringToFront();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()) {
            case R.id.sort_dropdown:
                populateTotalSpent(categoryDropdown, timeDropdown);
                break;

            case R.id.time_dropdown:
                populateTotalSpent(categoryDropdown, timeDropdown);
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private String[] populateUsers (ArrayList<LinkedAccount> superviseeNames) {
        String[] users = new String[superviseeNames.size() + 1];

        users[0] = "Current User";

        for (int i = 0; i < superviseeNames.size(); i++) {
            //users[i + 1] = superviseeNames.get(i).getUserName();
        }

        return users;
    }

    private String[] populateCategories (String[] categoryNames) {
        String[] categories = new String[categoryNames.length + 1];

        categories[0] = "Overall";

        for (int i = 0; i < categoryNames.length; i++)
            categories[i + 1] = categoryNames[i];

        return categories;
    }

    private void populateTotalSpent(Spinner catDD, Spinner timeDD) {

        String catSelected = (String) catDD.getSelectedItem();
        String time = (String) timeDD.getSelectedItem();

        ZonedDateTime end = ZonedDateTime.now();
        ZonedDateTime start;
        LinkedList<Expenditure> expList;
        float totalSpentValue;

        if (time.equals("Last 7 Days")) {
            // Set start time to be 7 days ago
            start = end.minusDays(7);
        }

        else if (time.equals("Last Month")) {
            // Set start time to be 1 month ago
            start = end.minusMonths(1);
        }

        else {
            // Set start time to be 3 months ago
            start = end.minusMonths(3);
        }

        // Calculate total spent for all categories if overall is selected
        if (catSelected.equals("Overall")) {
            expList = BMSApplication.expSystem.getExpendituresByDate(start, end);
            totalSpentValue = calcTotalSpent(expList);
        }


        // Calculate total spent for the selected category only
        else {
            expList = BMSApplication.expSystem.getExpendituresTimeAndCat(start, end, catSelected);
            totalSpentValue = calcTotalSpent(expList);
        }

        if (totalSpentValue == 0) {
            totalSpentView.setText(("Total Spent: $0.00"));
        }

        else {
            totalSpent = Float.valueOf(totalSpentValue).toString();
            totalSpentView.setText("Total Spent: $" + totalSpent);
        }

    }

    private float calcTotalSpent(LinkedList<Expenditure> expList) {

        float totalSpent = 0;

        if (expList == null)
            return totalSpent;

        ListIterator<Expenditure> it = expList.listIterator();

        while (it.hasNext())
            totalSpent += it.next().getValue();

        return totalSpent;
    }

}
