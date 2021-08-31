package processor;

import api.LoggingUtil;
import model.WatchItem;

/**
 * Calculates the total watch purchase cost for a particular type of watch based on the number of it in the cart which are not covered by the discount policy.
 */
public class NonDiscountedWatchCostCalculator implements ItemCostCalculator<WatchItem> {

    @Override
    public double getTotalCost(final WatchItem watchItem, int count) {

        LoggingUtil.log("Calculating Non Discounted Cost for Watch with Id : { " + watchItem.getItemId() + " }");

        if (0 == watchItem.getDiscountedUnitCount()) {

            return watchItem.getWatchPrice() * count;
        }

        return watchItem.getWatchPrice() * (count % watchItem.getDiscountedUnitCount());
    }
}