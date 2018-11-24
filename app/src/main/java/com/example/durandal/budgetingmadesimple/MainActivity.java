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

        testDatabase();  // Temporary
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
