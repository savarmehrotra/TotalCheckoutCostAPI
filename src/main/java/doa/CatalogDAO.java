package doa;

import java.util.Optional;

import exception.DataBaseException;
import model.CatalogItem;

public interface CatalogDAO<T extends CatalogItem>  {

    Optional<T> getProductData(String productKey)
            throws DataBaseException;
}