package doa;

import java.util.Optional;

import javax.inject.Inject;

import api.LoggingUtil;
import aws.accessor.DynamoDBAccessor;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.internal.IteratorSupport;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import exception.DataBaseException;
import model.WatchItem;

public class WatchCatalogueDAO implements CatalogDAO<WatchItem> {

    private final DynamoDBAccessor dynamoDBAccessor;

    @Inject
    public WatchCatalogueDAO(final DynamoDBAccessor dynamoDBAccessor) {

        this.dynamoDBAccessor = dynamoDBAccessor;
    }

    @Override
    public Optional<WatchItem> getProductData(final String itemId)
            throws DataBaseException {

        try {
            QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("itemId = :v_item_id")
                    .withValueMap(new ValueMap().withString(":v_item_id", itemId));

            ItemCollection<QueryOutcome> listOfItemIds = dynamoDBAccessor.queryWithQuerySpec(querySpec);

            IteratorSupport<Item, QueryOutcome> iteratorSupport = listOfItemIds.iterator();
            if (iteratorSupport.hasNext()) {

                return Optional.of(convertDDBItemToWatchItem(iteratorSupport.next()));
            }

            return Optional.empty();
        }
        catch (Exception ex) {

            final String ERROR_MESSAGE = "Exception : " + ex + "occurred while fetching Watch with Id: " + itemId;
            LoggingUtil.log(ERROR_MESSAGE);
            throw new DataBaseException(ERROR_MESSAGE, ex);
        }
    }

    private WatchItem convertDDBItemToWatchItem(final Item item) {

        LoggingUtil.log("DDB Item Received from Catalog Table : " + item.toJSONPretty());

        //TODO : Move strings to constants
        return WatchItem.builder()
                .itemId(item.getString("itemId"))
                .watchName(item.getString("watchName"))
                .watchPrice(Double.parseDouble(item.getString("watchPrice")))
                .discountedUnitCost(Double.parseDouble(item.getString("discountedUnitCost")))
                .discountedUnitCount(Integer.parseInt(item.getString("discountedUnitCount")))
                .build();

    }
}