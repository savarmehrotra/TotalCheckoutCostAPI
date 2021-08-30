package model;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public abstract class CatalogItem {

    @NonNull
    private String itemId;
}