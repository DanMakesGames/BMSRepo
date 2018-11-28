package com.example.durandal.budgetingmadesimple;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import java.util.regex.Pattern;

public class StatisticsActivity extends AppCompatActivity {

    private ListView expList;
    private EditText totalBudget;
    private EditText totalSpent;
    private EditText remaining;
    private Spinner timeDropdown;
    private Spinner sortDropdown;
    private Button personalButton;
    private Button superviseeButton;
    private Button groupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        // Buttons
        personalButton = findViewById(R.id.peronal_button);
        superviseeButton = findViewById(R.id.supervisee_button);
        groupButton = findViewById(R.id.group_button);

        // Text Boxes
        totalBudget = findViewById(R.id.total_bugdet);
        totalSpent = findViewById(R.id.total_spent);
        remaining = findViewById(R.id.remaining);

        // Dropdowns
        timeDropdown = findViewById(R.id.time_dropdown);
        sortDropdown = findViewById(R.id.sort_dropdown);

        String[] times = new String[]{"Last 7 Days", "Last Month", "Last 3 Months"};
        String[] sorts = new String[]{"Overall"};

        // Populate sorts based on current user's categories

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, times);
        timeDropdown.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sorts);
        sortDropdown.setAdapter(adapter2);


    }
}
