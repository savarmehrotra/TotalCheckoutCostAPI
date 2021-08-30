package dagger;

import javax.inject.Singleton;

import doa.WatchCatalogueDAO;
import processor.DiscountedWatchCostCalculator;
import processor.NonDiscountedWatchCostCalculator;
import processor.WatchListCondenser;

@Module(includes = { AWSModule.class, DataBaseModule.class })
public class ProcessorModule {

    @Provides
    @Singleton
    public NonDiscountedWatchCostCalculator provideNonDiscountedWatchCostCalculator() {

        return new NonDiscountedWatchCostCalculator();
    }

    @Provides
    @Singleton
    public DiscountedWatchCostCalculator provideDiscountedWatchCostCalculator() {

        return new DiscountedWatchCostCalculator();
    }

    @Provides
    @Singleton
    public WatchListCondenser provideWatchListCondenser(final WatchCatalogueDAO watchCatalogueDAO) {

        return new WatchListCondenser(watchCatalogueDAO);
    }
}