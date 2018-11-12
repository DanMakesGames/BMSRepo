package com.example.durandal.budgetingmadesimple;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    Database bmsDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Temporary
        // Create SQL database
        bmsDb = new Database(this);
        // Insert data
        boolean isInserted = bmsDb.insertData("Aaron Trefler");
        if(isInserted == true)
            Log.d("SQL Database Testing","Inserted");
        else
            Log.d("SQL Database Testing","Not inserted");
        // Retrieve data
        Cursor res = bmsDb.getData();
        if(res.getCount() == 0)
            Log.d("SQL Database Testing", "No data returned");
        else
            while (res.moveToNext()) {
                Log.d("SQL Database Testing", "Id: " + res.getString(0));
                Log.d("SQL Database Testing", "Username: " + res.getString(1));
            }
    }
}
