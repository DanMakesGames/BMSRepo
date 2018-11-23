package com.example.durandal.budgetingmadesimple;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private ListView expList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Test Expenditure.
        BMSApplication.expSystem.addExpenditure(100,"food", false, ReoccurringRate.NONE);
        BMSApplication.expSystem.addExpenditure(600,"video games", false, ReoccurringRate.NONE);

        // Set up initial list.
        expList = (ListView) findViewById(R.id.expList);

        Log.d("Debug: ","LENGTH:" + BMSApplication.expSystem.getExpendituresAll().toArray().length);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.activity_listview, BMSApplication.expSystem.getExpendituresAll().toArray() );
        expList.setAdapter(adapter);

    }
}
