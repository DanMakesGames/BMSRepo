package com.example.durandal.budgetingmadesimple;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
                if(clickItemObj.toString() == "supervisee")
                    /*Toast.makeText(AccountSettingsActivity.this, "fck " + clickItemObj.toString(), Toast.LENGTH_SHORT).show();*/
                if(clickItemObj.toString() == "logout")
                {
                    Intent intent = new Intent(AccountSettingsActivity.this, StartActivity.class);
                    startActivity(intent);
                    UserAccount.logout(AccountSettingsActivity.this);
                }
            }
        });
    }
}
