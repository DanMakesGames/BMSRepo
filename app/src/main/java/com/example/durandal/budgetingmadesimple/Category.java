package com.example.durandal.budgetingmadesimple;

public class Category {
    private float budget;
    private String name;

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
    public Category(float b, String n) {
        name = n;
        budget = b;
    }
}
