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

public class SettingsSupervisorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_supervisor);
        this.userListView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (toolbar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
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
        setTitle("Supervisors");

        final ArrayList<Map<String, Object>> itemDataList = new ArrayList<Map<String, Object>>();
        final UserAccount user = UserAccount.getUserAccount(SettingsSupervisorActivity.this);
        final ArrayList<LinkedAccount> supervisors = user.getSupervisors();

        updateItemDataList(itemDataList, supervisors);

        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, itemDataList, R.layout.supervisor_list_item,
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

                Toast.makeText(SettingsSupervisorActivity.this, "You select user " + userName + ", index: " + index + ", status: " + linkStatus, Toast.LENGTH_SHORT).show();

                String alertTitle = "";
                String alertText = "";
                String positiveButton = "Yes";
                String negativeButton = "No";
                switch (linkStatus) {
                    case LinkedAccount.REQUEST_SENT:
                        alertTitle = userName + " wants to be your supervisor";
                        alertText = "Accept link request from " + userName + "?\n" +
                                "All your information will be available to your supervisor.";
                        break;
                    case LinkedAccount.ACCEPTED:
                        alertTitle = "Linked";
                        alertText = "All your information is now available to " + userName + ".\n" +
                                "You need approval to unlink. Would you like to send unlink request now?";
                        break;
                    case LinkedAccount.DECLINED:
                        alertTitle = "Link Request Declined";
                        alertText = "You declined the link request from " + userName;
                        positiveButton = "Looks good";
                        negativeButton = "Back";
                        break;
                    case LinkedAccount.UNLINK_SENT:
                        alertTitle = "Unlink Request";
                        alertText = "Unlink request has been sent to "+userName+".\nYou need to wait for your supervisor's approval";
                        positiveButton = "OK";
                        negativeButton = "Keep Waiting";
                        break;
                    case LinkedAccount.UNLINK_GRANTED:
                        alertTitle = "Unlinked";
                        alertText = "You are currently not linked to " + userName + ".";
                        positiveButton = "Looks good";
                        negativeButton = "Back";
                        break;
                    case LinkedAccount.UNLINK_DENIED:
                        alertTitle = "Linked";
                        alertText = "You unlink request is not approved by " + userName + ".\n" +
                                "Send again?";
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsSupervisorActivity.this);
                builder.setTitle(alertTitle).setMessage(alertText)
                        .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (linkStatus == LinkedAccount.REQUEST_SENT) {
                                    if(user.addSupervisor(userId)){
                                        Toast.makeText(SettingsSupervisorActivity.this, "Accepted request from " + userName, Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(SettingsSupervisorActivity.this, "Failed. Something went wrong.", Toast.LENGTH_SHORT).show();
                                    }
                                } else if(linkStatus == LinkedAccount.ACCEPTED || linkStatus == LinkedAccount.UNLINK_DENIED){
                                        if(user.removeSupervisor(userId)){
                                            Toast.makeText(SettingsSupervisorActivity.this, "Accepted request from " + userName, Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(SettingsSupervisorActivity.this, "Failed. Something went wrong.", Toast.LENGTH_SHORT).show();
                                        }
                                }
                                updateItemDataList(itemDataList,supervisors);
                                simpleAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (linkStatus == LinkedAccount.REQUEST_SENT) {
                                    //decline link quest
                                    if (user.declineLinkRequest(userId))
                                        Toast.makeText(SettingsSupervisorActivity.this, "Declined request from " + userName, Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(SettingsSupervisorActivity.this, "Send failed", Toast.LENGTH_SHORT).show();
                                }
                                updateItemDataList(itemDataList,supervisors);
                                simpleAdapter.notifyDataSetChanged();
                            }
                        });
                builder.create().show();

            }
        });


    }

    private void updateItemDataList(ArrayList<Map<String, Object>> itemDataList, ArrayList<LinkedAccount> supervisors) {
        itemDataList.clear();
        for (LinkedAccount supervisor : supervisors) {
            //create item map for layout
            Map<String, Object> listItemMap = new HashMap<String, Object>();
            if (supervisor.isLinked())
                listItemMap.put("image", R.drawable.account_settings_user_linked);
            else
                listItemMap.put("image", R.drawable.account_settings_user_not_linked);
            listItemMap.put("name", supervisor.getUserName());
            listItemMap.put("email", supervisor.getUserEmail());
            listItemMap.put("userid", supervisor.getUserID());
            String status = "";
            int statusCode = supervisor.getStatus();
            switch (statusCode) {
                case LinkedAccount.REQUEST_SENT:
                    status = "Request Received";
                    break;
                case LinkedAccount.ACCEPTED:
                    status = "Linked";
                    break;
                case LinkedAccount.DECLINED:
                    status = "Declined";
                    break;
                case LinkedAccount.UNLINK_SENT:
                    status = "Unlink Request Sent";
                    break;
                case LinkedAccount.UNLINK_GRANTED:
                    status = "Unlinked";
                    break;
                case LinkedAccount.UNLINK_DENIED:
                    status = "Unlink Refused";
            }
            listItemMap.put("status", status);
            listItemMap.put("statusCode", statusCode);
            itemDataList.add(listItemMap);
        }
    }
}
