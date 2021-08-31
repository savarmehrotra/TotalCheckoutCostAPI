package model;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

/**
 * Data model for one line item of a watch in the catalog database
 */
@Data
@SuperBuilder
public class WatchItem extends CatalogItem {

    @NonNull
    private String watchName;

    @NonNull
    private Double watchPrice;

    private Integer discountedUnitCount;

    private Double discountedUnitCost;

}