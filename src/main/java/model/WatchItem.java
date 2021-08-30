package model;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

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