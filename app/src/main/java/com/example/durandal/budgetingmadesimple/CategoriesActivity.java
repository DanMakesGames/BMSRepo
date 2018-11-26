package com.example.durandal.budgetingmadesimple;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.view.View;
import android.app.AlertDialog;
import android.widget.EditText;
import android.content.DialogInterface;
import android.widget.Toast;

public class CategoriesActivity extends AppCompatActivity {

    private ListView expList;
    private String[] categoryNames;
    private EditText editBudget;
    private EditText addCategoryName;
    private CheckBox budgetCheckbox;
    private FloatingActionButton addCategoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        /* START TEST */
        // adding random stuff to categories for testing the list
        BMSApplication.expSystem.addCategory(false,0,"food");
        BMSApplication.expSystem.addCategory(false,0,"video games");
        BMSApplication.expSystem.addCategory(false,0,"test1");
        BMSApplication.expSystem.addCategory(false,0,"test2");
        /* END TEST */

        // Populate names array
        categoryNames = BMSApplication.expSystem.getCategoryNames();

        // link the listView and init the adapter
        expList = (ListView) findViewById(R.id.expList);
        final ArrayAdapter adapter = new ArrayAdapter(this, R.layout.activity_categories_listview, R.id.textCategory, categoryNames);
        expList.setAdapter(adapter);
        expList.setTextFilterEnabled(true);

        // Set onClickListener for the categories listed
        // TODO: Don't know how to get it to select the whole row.
        expList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // Get string of category selected
                String selected = expList.getItemAtPosition(position).toString();

                // Instead of going to another page, I just added an alert dialog
                AlertDialog.Builder builder=new AlertDialog.Builder(CategoriesActivity.this);
                final View alertView = getLayoutInflater().inflate(R.layout.activity_categories_alert_dialog, null);

                /* TODO set the 'hint' value in the editText on the alert dialog to the current budget...if none then default to
                *  'Enter Budget'
                *  Same thing goes for the checkbox, need to set the value based on if it's checked or not. Then set the value
                *  like that. */
                editBudget = (EditText) findViewById(R.id.editBudget);
                budgetCheckbox = (CheckBox) findViewById(R.id.budgetCheckbox);

                builder.setTitle("Category: "+selected)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // TODO this is the body for the confirm stuff
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // TODO for cancel dont do anything?
                            }
                        })
                        .setView(alertView);
                builder.create().show();
            }
        });

        // Link the floating Button
        addCategoryButton = (FloatingActionButton) findViewById(R.id.floatButton);
        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Instead of going to another page, I just added an alert dialog
                final AlertDialog.Builder builder=new AlertDialog.Builder(CategoriesActivity.this);
                final View alertView = getLayoutInflater().inflate(R.layout.activity_categories_add_alert_dialog, null);

                /* TODO set the 'hint' value in the editText on the alert dialog to the current budget...if none then default to
                 *  'Enter Budget'
                 *  Same thing goes for the checkbox, need to set the value based on if it's checked or not. Then set the value
                 *  like that. */
                editBudget = (EditText) alertView.findViewById(R.id.editBudgetAdd);
                budgetCheckbox = (CheckBox) alertView.findViewById(R.id.budgetCheckboxAdd);
                addCategoryName = (EditText) alertView.findViewById(R.id.editCategoryName);

                builder.setTitle("Create New Category")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // TODO

                                // Get the category name and budget amount
                                String catName = addCategoryName.getText().toString();
                                //double budgetAmount = Double.parseDouble(editBudget.getText().toString());
                                // Get checkbox boolean
                                //boolean isBudgetChecked = (boolean) budgetCheckbox.isChecked();

                                // Check for blank category name before adding
                                if (catName.isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "Category Not Added: No Name", Toast.LENGTH_SHORT);
                                } else {
                                    // Add the category to the list
                                    BMSApplication.expSystem.addCategory(false,0, catName);
                                }
                                BMSApplication.expSystem.addCategory(false,0, "yikes");
                                adapter.notifyDataSetChanged();
                                expList.invalidateViews();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // TODO for cancel dont do anything?
                            }
                        })
                        .setView(alertView);
                builder.create().show();
                adapter.notifyDataSetChanged();
            }
        });

    }

}
