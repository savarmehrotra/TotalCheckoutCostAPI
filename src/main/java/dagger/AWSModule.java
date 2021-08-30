package dagger;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.retry.PredefinedBackoffStrategies;
import com.amazonaws.retry.PredefinedRetryPolicies;
import com.amazonaws.retry.RetryPolicy;

import javax.inject.Singleton;

import aws.accessor.DynamoDBAccessor;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.DefaultAwsRegionProviderChain;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import dagger.Module;
import dagger.Provides;

@Module
public class AWSModule {

    private static String WATCH_CATALOG_TABLE_NAME = "WatchCatalog";

    @Provides
    @Singleton
    public AmazonDynamoDBClient provideDynamoDbClient() {

        ClientConfiguration clientConfig = new ClientConfiguration().withConnectionTimeout(2 * 1000)
                .withClientExecutionTimeout(40 * 1000)
                .withRequestTimeout(20 * 1000)
                .withRetryPolicy(new RetryPolicy(PredefinedRetryPolicies.DEFAULT_RETRY_CONDITION,
                        new PredefinedBackoffStrategies.ExponentialBackoffStrategy(100, 20000), 10, true))
                .withThrottledRetries(true);
        return (AmazonDynamoDBClient) AmazonDynamoDBClientBuilder.standard()
                .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
                .withClientConfiguration(clientConfig)
                .withRegion(new DefaultAwsRegionProviderChain().getRegion())
                .build();
    }

    @Provides
    @Singleton
    public DynamoDBMapper provideDynamoDBMapper(AmazonDynamoDBClient dynamoDBClient) {

        return new DynamoDBMapper(dynamoDBClient);
    }

    @Provides
    @Singleton
    public DynamoDBAccessor provideDDBAccessor(AmazonDynamoDBClient dynamoDBClient, DynamoDBMapper dynamoDBMapper) {

        return new DynamoDBAccessor(dynamoDBClient, dynamoDBMapper, WATCH_CATALOG_TABLE_NAME);
    }
}