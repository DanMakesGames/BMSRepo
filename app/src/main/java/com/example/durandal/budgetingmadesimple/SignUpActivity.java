package com.example.durandal.budgetingmadesimple;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    // TextFields
    private EditText editFirstName;
    private EditText editLastName;
    private EditText editUsername;
    private EditText editPassword;
    private EditText editConfirmPassword;

    // Buttons
    private Button signUpButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Link TextFields
        editFirstName       = (EditText) findViewById(R.id.edit_firstName);
        editLastName        = (EditText) findViewById(R.id.edit_lastName);
        editUsername        = (EditText) findViewById(R.id.edit_username);
        editPassword        = (EditText) findViewById(R.id.edit_password);
        editConfirmPassword = (EditText) findViewById(R.id.edit_confirmPassword);

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
                editFirstName.setEnabled(false);
                editLastName.setEnabled(false);
                editUsername.setEnabled(false);
                editPassword.setEnabled(false);
                editConfirmPassword.setEnabled(false);

                // Get textField inputs
                String firstName = editFirstName.getText().toString();
                String lastName = editLastName.getText().toString();
                String username = editUsername.getText().toString();
                String password = editPassword.getText().toString();
                String confirmPass = editConfirmPassword.getText().toString();

                // error flag
                boolean error = false;

                // Check for empty strings and valid strings
                if (firstName.length() < 0 || !Pattern.matches("[a-zA-Z]+", firstName)) {
                    editFirstName.setError("Invalid first name");
                    error = true;
                }

                if (lastName.length() < 0 || !Pattern.matches("[a-zA-Z]+", lastName)) {
                    editLastName.setError("Invalid last name");
                    error = true;
                }

                if (username.length() < 0 || !Pattern.matches("[a-zA-Z]+", username)) {
                    editUsername.setError("Invalid username");
                    error = true;
                }

                if (password.length() < 0) {
                    editPassword.setError("Should input password");
                    error = true;
                }

                if (confirmPass.length() < 0) {
                    editPassword.setError("Should input password");
                    error = true;
                }

                // Check for valid passwords
                if (!password.equals(confirmPass)) {
                    editConfirmPassword.setError("Passwords do not match");
                    error = true;
                }

                // Check valid username/doesn't already exist

                // Check for errors, reset fields
                if (error) {
                    signUpButton.setEnabled(true);
                    backButton.setEnabled(true);
                    editFirstName.setEnabled(true);
                    editLastName.setEnabled(true);
                    editUsername.setEnabled(true);
                    editPassword.setEnabled(true);
                    editConfirmPassword.setEnabled(true);

                    editPassword.setText("");
                    editConfirmPassword.setText("");
                }

                // Add to database

                // Segue to HomeScreenActivity if no errors. Else return back to this page (SignUpActivity)
            }
        }); // end signUpButton onClick
    }

}