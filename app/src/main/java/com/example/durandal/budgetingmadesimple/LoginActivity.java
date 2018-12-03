package com.example.durandal.budgetingmadesimple;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Login page. Enter password, Enter username. Press "Login". Test. Valid, go to loading activity.
 * False, report to use false, stay on page.
 *
 * After pressing login, all buttons and fields should become inoperable, until false login.
 */
public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private Button backButton;
    private EditText editPassword;
    private EditText editUsername;
    private TextView textErrorMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.login_button);
        backButton = (Button) findViewById(R.id.back);
        editPassword = (EditText) findViewById(R.id.edit_password);
        editUsername = (EditText) findViewById(R.id.edit_username);
        textErrorMessage = (TextView) findViewById(R.id.text_error_message);

        textErrorMessage.setAlpha(0);

        backButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // disable input
                loginButton.setEnabled(false);
                editPassword.setEnabled(false);
                editUsername.setEnabled(false);
                String username = editUsername.getText().toString();
                String password =  editPassword.getText().toString();

                // login name and password are successful, move to loading expenditures activity.
                if(BMSApplication.database.login( username, password)) {
                    // populate account.

                    // get account
                    Cursor userCursor = BMSApplication.database.getUser(username);

                    // create instantiate account.
                    if (userCursor.getCount() == 0)
                        Log.d("", "No data returned");
                    else{
                        while (userCursor.moveToNext()) {

                            BMSApplication.account = new UserAccount(
                                    Integer.parseInt(userCursor.getString(0)),
                                    userCursor.getString(1),
                                    userCursor.getString(3),
                                    userCursor.getString(2));
                        }
                    }

                    // populate expenditures and categories.
                    BMSApplication.expSystem.populateFromDatabase(username);

                    // Transition to mainPage.
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                }
                // login failed, reset
                else {
                    // re-enable button and text fields. Present message that login failed, and to try again.
                    editUsername.setEnabled(true);
                    editPassword.setEnabled(true);
                    loginButton.setEnabled(true);
                    editUsername.setText("");
                    editPassword.setText("");

                    textErrorMessage.setAlpha(1);
                }

            }
        });

    }
}
