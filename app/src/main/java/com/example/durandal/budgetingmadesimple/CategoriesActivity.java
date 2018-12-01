package com.example.durandal.budgetingmadesimple;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
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
    private EditText editBudget2; // lol
    private EditText editBudgetAdd;
    private EditText addCategoryName;
    private CheckBox budgetCheckbox;
    private CheckBox budgetCheckbox2; //lmao
    private FloatingActionButton addCategoryButton;
    private DrawerLayout mDrawerLayout;

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
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.activity_categories_listview, R.id.textCategory, categoryNames);
        expList.setAdapter(adapter);
        expList.setTextFilterEnabled(true);


        // code for navigation bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionbar.setTitle("Categories");

        //TextView drawer_text = (TextView) findViewById(R.id.drawer_header_text);
        //drawer_text.setText(BMSApplication.account.getUserName());

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
                        expList.bringToFront();

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
                                Intent intent1 = new Intent(CategoriesActivity.this, MainActivity.class);
                                startActivity(intent1);
                                break;
                            case R.id.nav_item_two: // go to categories page
                                Intent intent2 = new Intent(CategoriesActivity.this, CategoriesActivity.class);
                                startActivity(intent2);
                                break;
                            case R.id.nav_item_three: // go to statistics page
                                //Intent intent3 = new Intent(CategoriesActivity.this, StatisticsActivity.class);
                                //startActivity(intent3);
                                break;
                            case R.id.nav_item_four: // go to settings
                                //Intent intent4 = new Intent(CategoriesActivity.this, SettingsActivity.class);
                                //startActivity(intent4);
                                break;
                        }
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                }); // end of navigation bar code

        // Set onClickListener for the categories listed
        // TODO: Don't know how to get it to select the whole row.
        expList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // Get string of category selected
                final String selected = expList.getItemAtPosition(position).toString();

                // Instead of going to another page, I just added an alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(CategoriesActivity.this);
                final View alertView = getLayoutInflater().inflate(R.layout.activity_categories_alert_dialog, null);

                editBudget2 = (EditText) alertView.findViewById(R.id.editBudget2);
                budgetCheckbox2 = (CheckBox) alertView.findViewById(R.id.budgetCheckbox2);

                // Set the hint value and checkbox values
                // TODO: Need method to return true or false if a category has a budget or not
                // If (Category has a budget) {
                //      editBudget2.setHint([That category budget]);
                //      budgetCheckbox2.setChecked(false);
                // } Else {
                editBudget2.setHint("Enter a Budget");
                budgetCheckbox2.setChecked(false);
                // }

                builder.setTitle("Category: "+selected)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                final boolean isBudgetChecked = (boolean) budgetCheckbox2.isChecked();
                                String budgetStr = editBudget2.getText().toString();
                                double budgetAmnt = budgetStr.isEmpty() ? 0.0 : Double.parseDouble(budgetStr);

                                // Check the apply budget box
                                if (isBudgetChecked){
                                    if (budgetAmnt == 0) { // budget not set
                                        Toast.makeText(getApplicationContext(), selected+" Budget Not Set",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    else { // set the budget
                                        // TODO however you set the budget do it here
                                        Toast.makeText(getApplicationContext(), selected+" Budget Set",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else { // apply budget box not checked
                                    // TODO however you unset the budget do it here
                                    Toast.makeText(getApplicationContext(), selected+" Budget Unset",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // TODO for cancel don't do anything?
                            }
                        })
                        .setView(alertView);
                builder.create().show();
            }
        });
        expList.bringToFront();
        // Link the floating Button
        addCategoryButton = (FloatingActionButton) findViewById(R.id.floatButton);
        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Instead of going to another page, I just added an alert dialog
                final AlertDialog.Builder builder=new AlertDialog.Builder(CategoriesActivity.this);
                final View alertView2 = getLayoutInflater().inflate(R.layout.activity_categories_add_alert_dialog, null);

                /* TODO set the 'hint' value in the editText on the alert dialog to the current budget...if none then default to
                 *  'Enter Budget'
                 *  Same thing goes for the checkbox, need to set the value based on if it's checked or not. Then set the value
                 *  like that. */
                editBudgetAdd = (EditText) alertView2.findViewById(R.id.editBudgetAdd);
                budgetCheckbox = (CheckBox) alertView2.findViewById(R.id.budgetCheckboxAdd);
                addCategoryName = (EditText) alertView2.findViewById(R.id.editCategoryName);

                builder.setTitle("Create New Category")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Get the category name and budget amount
                                String catName = addCategoryName.getText().toString();
                                String budgetStr = editBudgetAdd.getText().toString();
                                double budgetAmnt = budgetStr.isEmpty() ? 0.0 : Double.parseDouble(budgetStr);
                                // Get checkbox boolean
                                boolean isBudgetChecked = (boolean) budgetCheckbox.isChecked();

                                // Check for blank category name before adding
                                if (catName.isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "Category Not Added: No Name Specified",
                                            Toast.LENGTH_SHORT).show();
                                } else if (budgetAmnt < 0.0){ // check for negative budget
                                    Toast.makeText(getApplicationContext(), "Category Not Added: Negative Budget",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    if ((budgetAmnt > 0.0) && (!isBudgetChecked)){ // budget input but not checked to confirm
                                        Toast.makeText(getApplicationContext(), "Category Added: Budget Not Set",
                                                Toast.LENGTH_SHORT).show();
                                        BMSApplication.expSystem.addCategory(false, 0, catName);
                                        //ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.activity_categories_listview, R.id.textCategory, categoryNames);
                                        //expList.setAdapter(adapter);
                                        finish();
                                        startActivity(getIntent());
                                    }
                                    else { // create the budget
                                        Toast.makeText(getApplicationContext(), "Category " + catName +" Added!",
                                                Toast.LENGTH_SHORT).show();
                                        BMSApplication.expSystem.addCategory(isBudgetChecked, (float)budgetAmnt, catName);
                                        finish();
                                        startActivity(getIntent());
                                    }
                                }
                                //adapter.notifyDataSetChanged();
                                //expList.invalidateViews();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // TODO for cancel don't do anything?
                            }
                        })
                        .setView(alertView2);
                builder.create().show();
            }
        });

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

}
