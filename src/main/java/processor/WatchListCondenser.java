package processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import api.LoggingUtil;
import doa.WatchCatalogueDAO;
import exception.DataBaseException;
import exception.ItemNotFoundException;
import lombok.NonNull;
import model.WatchItem;
import org.apache.commons.lang3.tuple.ImmutablePair;

/**
 * Creating ItemId and count pairings
 * Checking if the item exists in the catalog, and return the WatchItem and corresponding count
 */

public class WatchListCondenser {

    @NonNull
    private final WatchCatalogueDAO watchCatalogueDAO;

    @Inject
    public WatchListCondenser(final WatchCatalogueDAO watchCatalogueDAO) {

        this.watchCatalogueDAO = watchCatalogueDAO;
    }

    public List<ImmutablePair<WatchItem, Integer>> getCondensedWatchItemList(final List<String> ItemIds)
            throws ItemNotFoundException, DataBaseException {

        Map<String, Integer> itemIdCountMap = new HashMap<>();

        for (String itemId : ItemIds) {

            itemIdCountMap.put(itemId, itemIdCountMap.getOrDefault(itemId, 0) + 1);
        }

        List<ImmutablePair<WatchItem, Integer>> watchItemAndCountList = new ArrayList<>();

        for (String itemId : itemIdCountMap.keySet()) {

            LoggingUtil.log("Checking Watch Catalog Database For Watch with Id : " + itemId);
            Optional<WatchItem> watchItem = watchCatalogueDAO.getProductData(itemId);

            if (watchItem.isPresent()) {
                LoggingUtil.log("Found Watch with Id : { " + itemId + " }in the Catalog Database");
                LoggingUtil.log("Purchase Count for Watch with Id : {" + itemId + "} is {" + itemIdCountMap.get(itemId) +"}");
                watchItemAndCountList.add(new ImmutablePair<>(watchItem.get(), itemIdCountMap.get(itemId)));
            }
            else {
                String ERROR_MESSAGE = "Did Not Found Watch with Id {" + itemId + "} in Catalog Database";
                LoggingUtil.log(ERROR_MESSAGE);
                throw new ItemNotFoundException(ERROR_MESSAGE);
            }
        }

        return watchItemAndCountList;
    }
}