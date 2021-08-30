package processor;

import model.CatalogItem;

public interface ItemCostCalculator<I extends CatalogItem> {

    double getTotalCost(I catalogItem, int count);
}