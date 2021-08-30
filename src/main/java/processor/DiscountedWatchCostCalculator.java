package processor;

import api.LoggingUtil;
import model.WatchItem;

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