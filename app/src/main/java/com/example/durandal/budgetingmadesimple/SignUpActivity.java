package com.example.durandal.budgetingmadesimple;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    // TextFields
    private EditText editUsername;
    private EditText editPassword;
    private EditText editConfirmPassword;
    private EditText editSecretQuestion;
    private EditText editSecretAnswer;
    private EditText editEmail;

    // Buttons
    private Button signUpButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Link TextFields
        editUsername        = (EditText) findViewById(R.id.edit_username);
        editPassword        = (EditText) findViewById(R.id.edit_password);
        editConfirmPassword = (EditText) findViewById(R.id.edit_confirmPassword);
        editSecretQuestion = (EditText) findViewById(R.id.edit_secret_question);
        editSecretAnswer = (EditText) findViewById(R.id.edit_secret_answer);
        editEmail = (EditText) findViewById(R.id.edit_email);

        // Link Buttons
        signUpButton        = (Button)   findViewById(R.id.sign_up_button);
        backButton          = (Button)   findViewById(R.id.back_button);

        // backButton onClick
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Segue back to LoginActivity
                Intent intent = new Intent(SignUpActivity.this, StartActivity.class);
                startActivity(intent);
            }
        }); // end backButton onClick

        // signUpButton onClick
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set buttons and textFields as disabled
                signUpButton.setEnabled(false);
                backButton.setEnabled(false);
                editUsername.setEnabled(false);
                editPassword.setEnabled(false);
                editConfirmPassword.setEnabled(false);
                editEmail.setEnabled(false);
                editSecretAnswer.setEnabled(false);
                editSecretQuestion.setEnabled(false);


                // Get textField inputs
                String username = editUsername.getText().toString();
                String password = editPassword.getText().toString();
                String confirmPass = editConfirmPassword.getText().toString();
                String email = editEmail.getText().toString();
                String secretQuestion = editSecretQuestion.getText().toString();
                String secretAnswer = editSecretAnswer.getText().toString();

                // error flag
                boolean error = false;

                if (username.length() == 0 || !Pattern.matches("[a-zA-Z]+", username)) {
                    editUsername.setError("Invalid username");
                    error = true;
                }

                if (password.length() == 0) {
                    editPassword.setError("Should input password");
                    error = true;
                }

                if (confirmPass.length() == 0) {
                    editConfirmPassword.setError("Should input password again");
                    error = true;
                }

                // Check for valid passwords
                if (!password.equals(confirmPass)) {
                    editConfirmPassword.setError("Passwords do not match");
                    error = true;
                }

                if (email.length() == 0) {
                    editEmail.setError("Should input email");
                    error = true;
                }

                if (secretQuestion.length() == 0) {
                    editSecretQuestion.setError("Should input question");
                    error = true;
                }

                if (secretAnswer.length() == 0) {
                    editSecretAnswer.setError("Should input answer");
                    error = true;
                }

                // Check valid username/doesn't already exist
                Cursor attemptCursor = BMSApplication.database.getUser(username);
                if(attemptCursor.getCount() != 0) {
                    editUsername.setError("Username is taken");
                    error = true;
                }

                // Check for errors, reset fields, and return.
                if (error) {
                    signUpButton.setEnabled(true);
                    backButton.setEnabled(true);

                    editUsername.setEnabled(true);
                    editPassword.setEnabled(true);
                    editConfirmPassword.setEnabled(true);

                    editEmail.setEnabled(true);
                    editSecretQuestion.setEnabled(true);
                    editSecretAnswer.setEnabled(true);

                    editPassword.setText("");
                    editConfirmPassword.setText("");
                    return;
                }

                // Username and password combo are valid---
                BMSApplication.database.createUser(username,password,email,secretQuestion,
                        secretAnswer,0,0,0);

                // now that user is created we need to setup account.
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

                // go to MainPage.
                // Transition to mainPage.
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }); // end signUpButton onClick
    }

}