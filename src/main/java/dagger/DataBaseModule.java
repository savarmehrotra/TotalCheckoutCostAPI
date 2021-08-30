package dagger;

import javax.inject.Singleton;

import aws.accessor.DynamoDBAccessor;
import doa.WatchCatalogueDAO;

@Module(includes = {AWSModule.class})
public class DataBaseModule {

    @Provides
    @Singleton
    WatchCatalogueDAO provideWatchCatalogueDAO(final DynamoDBAccessor dynamoDBAccessor) {

        return new WatchCatalogueDAO(dynamoDBAccessor);
    }
}