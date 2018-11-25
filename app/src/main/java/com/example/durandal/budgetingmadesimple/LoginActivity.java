package com.example.durandal.budgetingmadesimple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


/**
 * Login page. Enter password, Enter username. Press "Login". Test. Valid, go to loading activity.
 * False, report to use false, stay on page.
 *
 * After pressing login, all buttons and fields should become inoperable, until false login.
 */
public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText editPassword;
    private EditText editUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.login_button);
        editPassword = (EditText) findViewById(R.id.edit_password);
        editUsername = (EditText) findViewById(R.id.edit_username);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // disable input
                loginButton.setEnabled(false);

                editPassword.setText("");
                editPassword.setEnabled(false);

                editUsername.setText("");
                editUsername.setEnabled(false);

                // login name and password are successful, move to loading expenditures activity.
                if(BMSApplication.database.login( editUsername.getText().toString(), editPassword.getText().toString())) {
                    // TODO switch to loading activity
                }
                else {
                    // TODO re-enable button and text fields. Present message that login failed, and to try again.
                }

            }
        });

    }
}
