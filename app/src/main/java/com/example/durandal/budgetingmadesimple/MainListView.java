package com.example.durandal.budgetingmadesimple;

public class MainListView {
    private String name = "";
    private boolean checked = false;
    private Expenditure expenditure;

    public MainListView() {
    }

    public MainListView(Expenditure expen) {
        this.expenditure = expen;
        this.name = expen.toString();
    }

    public MainListView(String name, boolean checked) {
        this.name = name;
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Expenditure getExpenditure() { return expenditure; }

    public void setExpenditure(Expenditure expen) {
        this.expenditure = expen;
    }

    public String toString() {
        return name;
    }

    public void toggleChecked() {
        checked = !checked;
    }

}