package com.example.durandal.budgetingmadesimple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/**
 * Login page. Enter password, Enter username. Press "Login". Test. Valid, go to loading activity.
 * False, report to use false, stay on page.
 *
 * After pressing login, all buttons and fields should become inoperable, until false login.
 */
public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }
}
