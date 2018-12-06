package com.example.durandal.budgetingmadesimple;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import java.time.ZonedDateTime;
import java.util.LinkedList;

public class delExpenditurePrompt extends MainActivity {

    Button yesButton;
    Button noButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_prompt);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (0.5 * width), (int) (0.25 * height));

        final int[] positions;
        final String catSelection;
        final String timeSelection;
        Bundle extra = getIntent().getExtras();
        if (extra == null) {
            positions = null;
            catSelection = ExpenditureSystem.ALL_CATEGORY;
            timeSelection = "all times";
        }
        else {
            positions = extra.getIntArray("Positions");
            catSelection = extra.getString("Category");
            timeSelection = extra.getString("Time");
        }

        yesButton = (Button)findViewById(R.id.yesButton);
        noButton = (Button) findViewById(R.id.noButton);

        yesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                ZonedDateTime now = ZonedDateTime.now();
                if (timeSelection.equals("all times")) {
                    mainList = new LinkedList<MainListView>();
                    LinkedList<Expenditure> array = BMSApplication.expSystem.getExpendituresByCategory(catSelection);
                    for (int i = 0; i < array.size(); i++) {
                        mainList.add(new MainListView(array.get(i)));
                    }
                } else if (timeSelection.equals("last 7 days")) {
                    mainList = new LinkedList<MainListView>();
                    LinkedList<Expenditure> array = BMSApplication.expSystem.getExpendituresTimeAndCat(
                            now.minusDays(7), now, catSelection);
                    for (int i = 0; i < array.size(); i++) {
                        mainList.add(new MainListView(array.get(i)));
                    }

                } else if (timeSelection.equals("this month")) {
                    mainList = new LinkedList<MainListView>();
                    LinkedList<Expenditure> array = BMSApplication.expSystem.getExpendituresTimeAndCat(
                            now.minusDays(now.getDayOfMonth()),
                            now,
                            catSelection);
                    for (int i = 0; i < array.size(); i++) {
                        mainList.add(new MainListView(array.get(i)));
                    }
                }

                for (int i = 0; i < MainActivity.mainList.size(); i++) {
                    if (positions[i] == 1) {
                        Boolean a = BMSApplication.expSystem.deleteExpenditure(MainActivity.mainList.get(i).getExpenditure());
                    }
                }
                startActivity(new Intent(delExpenditurePrompt.this, MainActivity.class));
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                startActivity(new Intent(delExpenditurePrompt.this, MainActivity.class));
            }
        });

    }


}
