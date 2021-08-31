package processor;

import api.LoggingUtil;
import model.WatchItem;

/**
 * Calculates the total watch purchase cost for a particular type of watch based on the number of it in the cart which are applicable for the discount policy.
 */
public class DiscountedWatchCostCalculator implements ItemCostCalculator<WatchItem> {

    @Override
    public double getTotalCost(final WatchItem watchItem, int count) {

        LoggingUtil.log("Calculating the Discounted Cost for Watch with Id : { " + watchItem.getItemId() + " }");
        int discountedUnits = getDiscountedUnitCount(watchItem, count);
        return discountedUnits * watchItem.getDiscountedUnitCost();
    }

    private int getDiscountedUnitCount(final WatchItem watchItem, int count) {

        if (0 == watchItem.getDiscountedUnitCount()) {

            return 0;
        }

        return count / watchItem.getDiscountedUnitCount();
    }
}