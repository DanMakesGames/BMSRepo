package com.example.durandal.budgetingmadesimple;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SettingsSuperviseeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_supervisee);
        this.userListView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (toolbar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    // dont know what is doing
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void userListView() {
        setTitle("Supervisees");

        ArrayList<Map<String, Object>> itemDataList = new ArrayList<Map<String, Object>>();
        final UserAccount user = UserAccount.getUserAccount(SettingsSuperviseeActivity.this);
        ArrayList<LinkedAccount> supervisees = user.getSupervisees();
        final int userNum = supervisees.size();

        for (LinkedAccount supervisee : supervisees) {
            //create item map for layout
            Map<String, Object> listItemMap = new HashMap<String, Object>();
            if (supervisee.isLinked())
                listItemMap.put("image", R.drawable.account_settings_user_linked);
            else
                listItemMap.put("image", R.drawable.account_settings_user_not_linked);
            listItemMap.put("name", supervisee.getUserName());
            listItemMap.put("email", supervisee.getUserEmail());
            listItemMap.put("userid", supervisee.getUserID());
            String status = "";
            switch (supervisee.getStatus()) {
                case LinkedAccount.REQUEST_SENT:
                    status = "Request Sent";
                    break;
                case LinkedAccount.ACCEPTED:
                    status = "Linked";
                    break;
                case LinkedAccount.DECLINED:
                    status = "Declined";
                    break;
                case LinkedAccount.UNLINK_SENT:
                    status = "Unlink Request";
                    break;
                case LinkedAccount.UNLINK_GRANTED:
                    status = "Unlinked";
                    break;
                case LinkedAccount.UNLINK_DENIED:
                    status = "Linked";
            }
            listItemMap.put("status", status);
            listItemMap.put("statusCode", status);
            itemDataList.add(listItemMap);
        }
        //add supervisee button
        Map<String, Object> listItemMap = new HashMap<String, Object>();
        listItemMap.put("image", R.drawable.account_settings_plus);
        listItemMap.put("name", "Add");
        listItemMap.put("email", "Send a link request");
        listItemMap.put("status", "");
        listItemMap.put("statusCode", -1);
        listItemMap.put("userid", -1);
        itemDataList.add(listItemMap);


        SimpleAdapter simpleAdapter = new SimpleAdapter(this, itemDataList, R.layout.supervisor_list_item,
                new String[]{"image", "name", "email", "status"}, new int[]{R.id.userImage, R.id.userName, R.id.userEmail, R.id.linkStatus});

        ListView listView = (ListView) findViewById(R.id.settingsList);
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                Object clickItemObj = adapterView.getAdapter().getItem(index);
                HashMap clickItemMap = (HashMap) clickItemObj;
                final String userName = (String) clickItemMap.get("name");
                String userEmail = (String) clickItemMap.get("email");
                final int linkStatus = (int) clickItemMap.get("statusCode");
                final int userId = (int) clickItemMap.get("userid");
                View alertView = getLayoutInflater().inflate(R.layout.add_supervisee_view, null);
                final EditText inputName = (EditText) alertView.findViewById(R.id.username);

                Toast.makeText(SettingsSuperviseeActivity.this, "You select user " + userName + ", index: " + index + ", status: " + linkStatus, Toast.LENGTH_SHORT).show();

                if (index == userNum) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SettingsSuperviseeActivity.this);
                    builder.setTitle("Add Supervisee").setMessage("Input the username to send a link request.")
                            .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String nameToAdd = inputName.getText().toString();
                                    if (nameToAdd.equals(user.getUserName())) {
                                        Toast.makeText(SettingsSuperviseeActivity.this, "You cannot add yourself.", Toast.LENGTH_SHORT).show();
                                    }
                                    if (user.addSupervisee(nameToAdd)) {
                                        Toast.makeText(SettingsSuperviseeActivity.this, "Request send to " + nameToAdd, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(SettingsSuperviseeActivity.this, "Please make sure the name you input is valid and not linked.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).setView(alertView);
                    builder.create().show();
                } else {
                    String alertTitle = "";
                    String alertText = "";
                    String positiveButton = "Yes";
                    String negativeButton = "No";
                    switch (linkStatus) {
                        case LinkedAccount.REQUEST_SENT:
                            alertTitle = "Link Request Sent";
                            alertText = "Waiting for " + userName + " to accept to be your supervisee.";
                            positiveButton = "OK";
                            negativeButton = "Keep Waiting";
                            break;
                        case LinkedAccount.ACCEPTED:
                            alertTitle = "Linked";
                            alertText = "Unlink " + userName + "?";
                            break;
                        case LinkedAccount.DECLINED:
                            alertTitle = "Link Request Declined";
                            alertText = "Send request to " + userName + " again?";
                            break;
                        case LinkedAccount.UNLINK_SENT:
                            alertTitle = "Unlink Request";
                            alertText = userName + " request to unlink. Approve?";
                            break;
                        case LinkedAccount.UNLINK_GRANTED:
                            alertTitle = "Unlinked";
                            alertText = "Send request to " + userName + " again?";
                            break;
                        case LinkedAccount.UNLINK_DENIED:
                            alertTitle = "Linked";
                            alertText = "Unlink " + userName + "?";
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(SettingsSuperviseeActivity.this);
                    builder.setTitle(alertTitle).setMessage(alertText)
                            .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (linkStatus == LinkedAccount.REQUEST_SENT) {
                                        //do nothing
                                    } else if (linkStatus == LinkedAccount.DECLINED || linkStatus == LinkedAccount.UNLINK_GRANTED) {
                                        //send again
                                        if (user.addSupervisee(userName))
                                            Toast.makeText(SettingsSuperviseeActivity.this, "Request send to " + userName, Toast.LENGTH_SHORT).show();
                                        else
                                            Toast.makeText(SettingsSuperviseeActivity.this, "Request did not send", Toast.LENGTH_SHORT).show();
                                    } else {//linkstatus == linked
                                        //unlink
                                        if (user.removeSupervisee(userId))
                                            Toast.makeText(SettingsSuperviseeActivity.this, "Unlinked " + userName, Toast.LENGTH_SHORT).show();
                                        else
                                            Toast.makeText(SettingsSuperviseeActivity.this, "Unlink failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (linkStatus == LinkedAccount.UNLINK_SENT) {
                                        //deny unlink quest
                                        if (user.denyUnlinkRequest(userId))
                                            Toast.makeText(SettingsSuperviseeActivity.this, "Refused to unlink " + userName, Toast.LENGTH_SHORT).show();
                                        else
                                            Toast.makeText(SettingsSuperviseeActivity.this, "Send failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    builder.create().show();
                }
            }
        });


    }
}

