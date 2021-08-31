package model;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

/**
 * Implement to define the model of any item part of the catalog database.
 * */
@Data
@SuperBuilder
public abstract class CatalogItem {

    @NonNull
    private String itemId;
}