package com.example.durandal.budgetingmadesimple;

import android.widget.CheckBox;
import android.widget.TextView;

/** Holds child views for one row. */
public class MainListViewHolder {
    private CheckBox checkBox;
    private TextView textView;

    public MainListViewHolder() {
    }

    public MainListViewHolder(TextView textView, CheckBox checkBox) {
        this.checkBox = checkBox;
        this.textView = textView;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}