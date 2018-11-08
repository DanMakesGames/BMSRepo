import android.os.Build;
import android.support.annotation.RequiresApi;

import java.time.Instant;
import java.time.LocalDateTime;

enum ReoccurringRate {
    NONE, WEEKLY, MONTHLY;
}

public class Expenditure {


    public Instant getTimeStamp() {
        return timeStamp;
    }

    // Time of creation for the expenditure. Most useful as a proper ID for the expenditure, seeing
    // as you can't make two expenditures on the exact nano second.
    private final Instant timeStamp;

    public float getValue() {
        return value;
    }



    // Monitary value of the expenditure. This is the Money ($) spent.
    private final float value;

    public String getCategory() {
        return category;
    }



    /*
    * This needs to be updated so that the database is updated with the change */
    public void setCategory(String category) {
        this.category = category;
    }

    // String for category. Remember, this string can be used to get the Category object entry from
    // the category data structure.
    private String category;



    public boolean isReocurring() {
        return bIsReocurring;
    }

    // Controls if this expenditure is reoccurring.
    private final boolean bIsReocurring;



    public ReoccurringRate getRate() {
        return rate;
    }

    // Controls rate of reoccurrance.
    private final ReoccurringRate rate;


    @RequiresApi(api = Build.VERSION_CODES.O)
    Expenditure(float inValue, String inCategory) {

        timeStamp = Instant.now();
        value = inValue;
        category = inCategory;
        bIsReocurring = false;
        rate = ReoccurringRate.NONE;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    Expenditure(float inValue, String inCategory, boolean inReocurring,ReoccurringRate inRate) {

        timeStamp = Instant.now();
        value = inValue;
        category = inCategory;
        bIsReocurring = inReocurring;
        rate = inRate;

    }


}
