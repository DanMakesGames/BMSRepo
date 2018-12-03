package com.example.durandal.budgetingmadesimple;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AccountSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        this.arrayAdapterListView();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
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


    // This method use an ArrayAdapter to add data in ListView.
    private void arrayAdapterListView()
    {
        setTitle("Account Settings");
        List<String> dataList = new ArrayList<String>();
        dataList.add("supervisor");
        dataList.add("supervisee");
        dataList.add("log out");


        ListView listView = (ListView)findViewById(R.id.listViewExample);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                Object clickItemObj = adapterView.getAdapter().getItem(index);
                if(clickItemObj.toString() == "supervisor")
                {
                    /*Intent intent = new Intent(AccountSettingsActivity.this, StartActivity.class);
                    startActivity(intent);*/
                }
                if(clickItemObj.toString().equals("supervisee")){
                    /*Toast.makeText(AccountSettingsActivity.this, "fck " + clickItemObj.toString(), Toast.LENGTH_SHORT).show();*/
                    Intent intent = new Intent(AccountSettingsActivity.this,SettingsSuperviseeActivity.class);
                    startActivity(intent);
                }

                if(clickItemObj.toString().equals("logout"))
                {
                    UserAccount.logout(AccountSettingsActivity.this);
                    Intent intent = new Intent(AccountSettingsActivity.this, StartActivity.class);
                    startActivity(intent);

                }
            }
        });
    }
}
