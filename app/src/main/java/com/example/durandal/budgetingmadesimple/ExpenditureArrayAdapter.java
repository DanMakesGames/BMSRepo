package com.example.durandal.budgetingmadesimple;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/** Custom adapter for displaying an array of Planet objects. */
public class ExpenditureArrayAdapter extends ArrayAdapter<MainListView> {

    private LayoutInflater inflater;

    public ExpenditureArrayAdapter(Context context, List<MainListView> mainList) {
        super(context, R.layout.simple_row, R.id.label, mainList);
        //Cache the LayoutInflate to avoid asking for a new one each time.
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        MainListView expenView = (MainListView) this.getItem(position);
        CheckBox checkBox;
        TextView textView;

        // Create a new row view
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.simple_row, null);

            textView = (TextView) convertView.findViewById(R.id.label);
            checkBox = (CheckBox) convertView.findViewById(R.id.CheckBox01);

            convertView.setTag(new MainListViewHolder(textView, checkBox));

            // If CheckBox is toggled, update the expenditure it is tagged with.
            checkBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    MainListView expenditureView = (MainListView) cb.getTag();
                    expenditureView.setChecked(cb.isChecked());
                    System.out.println(expenditureView.getName());
                    if (hasSelected(MainActivity.mainList)) {
                        MainActivity.fab.hide();
                        MainActivity.delFab.show();
                    }
                    else {
                        MainActivity.delFab.hide();
                        MainActivity.fab.show();
                    }
                }
            });
        }
        // Re-use existing row view
        else {

            MainListViewHolder viewHolder = (MainListViewHolder) convertView
                    .getTag();
            checkBox = viewHolder.getCheckBox();
            textView = viewHolder.getTextView();
        }

        checkBox.setTag(expenView);

        // Display planet data
        checkBox.setChecked(expenView.isChecked());
        textView.setText(expenView.getName());

        return convertView;
    }

    public boolean hasSelected(List<MainListView> l) {
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).isChecked()) {
                return true;
            }
        }
        return false;
    }

}