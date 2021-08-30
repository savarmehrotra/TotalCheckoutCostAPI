package aws.accessor;

import javax.inject.Inject;

import api.LoggingUtil;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import lombok.NonNull;


public class DynamoDBAccessor {

    private final AmazonDynamoDB ddbClient;
    private final DynamoDB dynamoDB;
    private final DynamoDBMapper dynamoDBMapper;
    private Table table;

    @Inject
    public DynamoDBAccessor(@NonNull AmazonDynamoDB ddbClient, @NonNull DynamoDBMapper dynamoDBMapper, @NonNull String tableName) {
        this.ddbClient = ddbClient;
        this.dynamoDBMapper = dynamoDBMapper;
        this.dynamoDB = new DynamoDB(this.ddbClient);
        this.table = dynamoDB.getTable(tableName);
    }

    public ItemCollection<QueryOutcome> queryWithQuerySpec(@NonNull QuerySpec querySpec) {

        LoggingUtil.log("Querying Table : {} " + table.getTableName());
        return table.query(querySpec);
    }
}