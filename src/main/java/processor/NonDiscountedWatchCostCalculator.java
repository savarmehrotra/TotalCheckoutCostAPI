package processor;

import api.LoggingUtil;
import model.WatchItem;

public class NonDiscountedWatchCostCalculator implements ItemCostCalculator<WatchItem> {

    @Override
    public double getTotalCost(final WatchItem watchItem, int totalCount) {

        LoggingUtil.log("Calculating Non Discounted Cost for Watch with Id : { " + watchItem.getItemId() + " }");

        if (0 == watchItem.getDiscountedUnitCount()) {

            return watchItem.getWatchPrice() * totalCount;
        }

        return watchItem.getWatchPrice() * (totalCount % watchItem.getDiscountedUnitCount());
    }
}