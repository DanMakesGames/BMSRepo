package com.example.durandal.budgetingmadesimple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

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
        categories = populateCategories(BMSApplication.expSystem.getCategoryNames());

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
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, users);
        superviseeDropdown.setAdapter(adapter3);

        // Set listeners
        timeDropdown.setOnItemSelectedListener(this);
        categoryDropdown.setOnItemSelectedListener(this);
        superviseeDropdown.setOnItemSelectedListener(this);

        //By default, have last month as the time selected
        timeDropdown.setSelection(1);

        // By default, have the current user as the user selected
        superviseeDropdown.setSelection(0);

        // By default, have overall total spent selected
        categoryDropdown.setSelection(0);

        // Set the text views with their default values
        totalBudgetView.setText(totalbudget);
        remainingView.setText(remaining);

    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()) {
            case R.id.category_dropdown:
                onCategorySelected((String) categoryDropdown.getSelectedItem());
                break;

            case R.id.time_dropdown:
                populateTotalSpent(position);
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private String[] populateUsers (ArrayList<Account> superviseeNames) {
        String[] users = new String[superviseeNames.size() + 1];

        users[0] = "Current User";

        for (int i = 0; i < superviseeNames.size(); i++) {
            //users[i + 1] = supervisees.get(i).getUsername
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

    private void populateTotalSpent(int time) {

        ZonedDateTime end = ZonedDateTime.now();
        ZonedDateTime start;
        LinkedList<Expenditure> expList;
        float totalSpentValue;

        if (time == 0) {
            // Set start time to be 7 days ago
            start = end.minusDays(7);
        }

        else if (time == 1) {
            // Set start time to be 1 month ago
            start = end.minusMonths(1);
        }

        else {
            // Set start time to be 3 months ago
            start = end.minusMonths(3);
        }

        // Calculate total spent for all categories if overall is selected
        if (catSelected == "Overall")
            totalSpentValue = calcTotalSpentOverall(start, end);


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

    private void onCategorySelected(String cat) {
        // Based on which category was selected, populate a list of expenditures
        catSelected = cat;

        populateTotalSpent(timeDropdown.getSelectedItemPosition());
    }

    private float calcTotalSpent(LinkedList<Expenditure> expList) {
        ListIterator<Expenditure> it = expList.listIterator();
        float totalSpent = 0;

        while (it.hasNext())
            totalSpent += it.next().getValue();

        return totalSpent;
    }

    private float calcTotalSpentOverall(ZonedDateTime start, ZonedDateTime end) {
        float totalSpent = 0;

        for (String category : BMSApplication.expSystem.getCategoryNames()) {
            LinkedList<Expenditure> expList =
                    BMSApplication.expSystem.getExpendituresTimeAndCat(start, end, category);
            totalSpent += calcTotalSpent(expList);
        }

        return totalSpent;
    }

}
