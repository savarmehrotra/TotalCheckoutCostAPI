package processor;

import model.CatalogItem;

/**
 * Responsible to calculate the discounted or non discounted cost for any item that exits in the catalog database
 */
public interface ItemCostCalculator<I extends CatalogItem> {

    double getTotalCost(I catalogItem, int count);
}