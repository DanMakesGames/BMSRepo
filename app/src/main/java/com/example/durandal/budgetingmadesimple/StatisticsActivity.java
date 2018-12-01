package com.example.durandal.budgetingmadesimple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class StatisticsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // The app's text vies display total budget, total spent, and the remaining amount available
    private TextView totalBudgetView;
    private TextView totalSpentView;
    private TextView remainingView;

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
    private Account[] supervisees;

    private String totalbudget = "Total Budget: N/A"; // Only modified when we check expenditures of the last month
    private String totalSpent; // Get total spent based on category selected and
    private String remaining = "Remaining: N/A"; // Only modified when we check expenditures of the last month
    private String catSelected; // Current category selected

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        // Buttons
        personalButton = findViewById(R.id.peronal_button);
        groupButton = findViewById(R.id.group_button);

        // Text Boxes
        totalBudgetView = findViewById(R.id.total_bugdet);
        totalSpentView = findViewById(R.id.total_spent);
        remainingView = findViewById(R.id.remaining);

        // Dropdowns
        timeDropdown = findViewById(R.id.time_dropdown);
        categoryDropdown = findViewById(R.id.sort_dropdown);
        superviseeDropdown = findViewById(R.id.supervisee_dropdown);

        // Populate the times
        times = new String[]{"Last 7 Days", "Last Month", "Last 3 Months"};

        // Populate categories based on user's categories
        categories = BMSApplication.expSystem.getCategoryNames();

        // Populate the users array with the current user and it's supervisees
        //users = populateUsers(BMSApplication.account.getSupervisees());
        users = new String[]{"Current User"};

        // Populate the time dropdown
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, times);
        timeDropdown.setAdapter(adapter);

        // Populate the categories dropdown
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        categoryDropdown.setAdapter(adapter2);

        // Populate the supervisee dropdown
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        superviseeDropdown.setAdapter(adapter3);

        // Set listeners
        timeDropdown.setOnItemSelectedListener(this);
        categoryDropdown.setOnItemSelectedListener(this);
        superviseeDropdown.setOnItemSelectedListener(this);

        // Set the text views with their default values
        totalBudgetView.setText(totalbudget);
        remainingView.setText(remaining);

    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()) {
            case R.id.category_dropdown:
                onCategorySelected(position);
                break;

            case R.id.time_dropdown:
                onTimeSelected(position);
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private String[] populateUsers (ArrayList<Account> superviseeNames) {
        String[] users = new String[superviseeNames.size() + 1];

        users[0] = "Current User";

        for (int i = 1; i < superviseeNames.size(); i++) {
            //users[i] = supervisees.get(i-1).getUsername
        }

        return users;
    }

    public void onTimeSelected(int position) {

        if (position == 0) {
            // Set total spent based on last 7 days
            /*
            someList = getExpendituresTimeAndCat(currentTime - 7, currentTime, catSelected
             */
        }

        else if (position == 1) {
            // Set total spent based on last 7 days
            /*
            someList = getExpendituresTimeAndCat(currentTime - 30, currentTime, catSelected);
             */

            /*
             if (totalBudget)
                totalBudget - totalSpent;
              */
        }

        else {
            // Set total spend based on last 3 months
            /*
            someList = getExpendituresTimeAndCat(currentTime - 90, currentTime, catSelected);
             */
        }
    }

    public void onCategorySelected(int position) {
        // Based on which category was selected, populate a list of expenditures

        // catSelected = item spinner has currently selected.

    }




}
