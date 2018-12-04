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

public class ChangePasswordActivity extends AppCompatActivity{
    private Button changePasswordButton;
    private Button backButton;
    private EditText editOldPassword;
    private EditText editNewPassword;
    private EditText editUsername;
    private TextView textErrorMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        changePasswordButton = (Button) findViewById(R.id.login_button);
        backButton = (Button) findViewById(R.id.back);
        editOldPassword = (EditText) findViewById(R.id.edit_oldpassword);
        editUsername = (EditText) findViewById(R.id.edit_username);
        editNewPassword = (EditText) findViewById(R.id.edit_newpassword);
        textErrorMessage = (TextView) findViewById(R.id.text_error_message);

        textErrorMessage.setAlpha(0);

        backButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangePasswordActivity.this, AccountSettingsActivity.class);
                startActivity(intent);
            }
        });


        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // disable input
                changePasswordButton.setEnabled(false);
                editOldPassword.setEnabled(false);
                editUsername.setEnabled(false);
                String username = editUsername.getText().toString();
                String oldpassword =  editOldPassword.getText().toString();
                String newpassword =  editNewPassword.getText().toString();
                // login name and password are successful, move to loading expenditures activity.
                if(BMSApplication.database.login( username, oldpassword)) {
                    // populate account.

                    // get account
                    Cursor userCursor = BMSApplication.database.getUser(username);

                    // create instantiate account.
                    if (userCursor.getCount() == 0)
                        Log.d("", "No data returned");
                    else{
                        while (userCursor.moveToNext()) {

                            BMSApplication.database.updateUser(userCursor.getInt(0),
                                    userCursor.getString(1),
                                    newpassword,
                                    userCursor.getString(3),
                                    userCursor.getString(4),
                                    userCursor.getString(5),
                                    userCursor.getFloat(6),
                                    userCursor.getFloat(7),
                                    userCursor.getFloat(8));
                        }
                    }


                    // Transition to mainPage.
                    Intent intent = new Intent(ChangePasswordActivity.this, AccountSettingsActivity.class);
                    startActivity(intent);

                }
                // change password failed, reset
                else {
                    // re-enable button and text fields. Present message that login failed, and to try again.
                    editUsername.setEnabled(true);
                    editOldPassword.setEnabled(true);
                    editNewPassword.setEnabled(true);
                    changePasswordButton.setEnabled(true);
                    editUsername.setText("");
                    editOldPassword.setText("");

                    textErrorMessage.setAlpha(1);
                }

            }
        });

    }
}
