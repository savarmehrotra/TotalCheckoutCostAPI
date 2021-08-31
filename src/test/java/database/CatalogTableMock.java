package database;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import database.constant.CatalogTableConstants;
import model.WatchItem;

public class CatalogTableMock {

    private static Map<String, WatchItem> table;

    static {
        table = new HashMap<>();
        table.put(CatalogTableConstants.SAMPLE_ITEM_ID_1, WatchItem.builder()
                .itemId("001")
                .watchName("Rolex")
                .watchPrice(100.0)
                .discountedUnitCount(3)
                .discountedUnitCost(200.0)
                .build());
        table.put(CatalogTableConstants.SAMPLE_ITEM_ID_2, WatchItem.builder()
                .itemId("002")
                .watchName("Michael Kors")
                .watchPrice(80.0)
                .discountedUnitCount(2)
                .discountedUnitCost(120.0)
                .build());
        table.put(CatalogTableConstants.SAMPLE_ITEM_ID_3, WatchItem.builder()
                .itemId("003")
                .watchName("Swatch")
                .watchPrice(50.0)
                .discountedUnitCount(0)
                .discountedUnitCost(0.0)
                .build());
        table.put(CatalogTableConstants.SAMPLE_ITEM_ID_4, WatchItem.builder()
                .itemId("004")
                .watchName("Casio")
                .watchPrice(30.0)
                .discountedUnitCount(0)
                .discountedUnitCost(0.0)
                .build());
    }

    public static Optional<WatchItem> getItem(String itemId) {

        if(table.containsKey(itemId)) {

            return Optional.of(table.get(itemId));
        }
        return Optional.empty();
    }
}
