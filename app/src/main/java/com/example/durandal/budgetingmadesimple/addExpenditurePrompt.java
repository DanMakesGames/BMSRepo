package com.example.durandal.budgetingmadesimple;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.ArrayUtils;

public class addExpenditurePrompt extends MainActivity {

    Spinner categories;
    TextView amount;
    Button cancel;
    Button done;
    private final String[] selCat = {ExpenditureSystem.SEL_CATEGORY};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenditure_prompt);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(0.6*width),(int)(0.6*height));

        amount = (TextView) findViewById(R.id.entryAmountText);
        done = (Button)findViewById(R.id.doneButton);
        cancel = (Button)findViewById(R.id.cancelButton);
        categories = (Spinner)findViewById(R.id.categoryList);


        done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (amount.getText().length() > 0 ) {
                    float num = Float.parseFloat(amount.getText().toString());
                    Expenditure newExpen = new Expenditure(num,categories.getSelectedItem().toString());
                    BMSApplication.expSystem.addExpenditure(newExpen);
                    startActivity(new Intent(addExpenditurePrompt.this, MainActivity.class));
                }
                else {
                    Toast.makeText(addExpenditurePrompt.this, "Please enter number", Toast.LENGTH_LONG).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(addExpenditurePrompt.this, MainActivity.class));
            }
        });


        ArrayAdapter categoryAdapter = new ArrayAdapter(this,R.layout.our_spinner_item,
                ArrayUtils.concat(selCat,
                        BMSApplication.expSystem.getCategoryNames()));
        categoryAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        categories.setAdapter(categoryAdapter);
        categories.setSelection(0);
        categories.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);


    }

}
