package com.example.durandal.budgetingmadesimple;

public class Category {
    private float budget;



    private boolean bIsBudgeted;
    private String name;


    public boolean isbIsBudgeted() {
        return bIsBudgeted;
    }

    public void setbIsBudgeted(boolean bIsBudgeted) {
        this.bIsBudgeted = bIsBudgeted;
    }


    /**
     * getBudget
     * @return the amount for this category
     */
    public float getBudget() {
        return budget;
    }

    /**
     * updateBudget
     * @param b - budget to change the value with
     */
    public void updateBudget(float b) {
        budget = b;
    }

    /**
     * getName
     * @return name of the category
     */
    public String getName() {
        return name;
    }

    /**
     * setName
     * @param n - name of category
     */
    public void setName(String n) {
        name = n;
    }

    /**
     * Constructor
     * @param b - budget value
     * @param n - name of the category
     */
    public Category(boolean inBBudget, float b, String n) {
        name = n;
        budget = b;
        bIsBudgeted = inBBudget;
    }

    @Override public String toString() {
        return name;
    }
}
