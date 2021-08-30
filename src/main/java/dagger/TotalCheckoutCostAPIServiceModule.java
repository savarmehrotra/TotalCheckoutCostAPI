package dagger;

import javax.inject.Singleton;

import processor.DiscountedWatchCostCalculator;
import processor.NonDiscountedWatchCostCalculator;
import processor.WatchListCondenser;
import service.TotalCheckoutCostAPIService;

@Module(includes = {ProcessorModule.class})
public class TotalCheckoutCostAPIServiceModule {

    @Provides
    @Singleton
    public TotalCheckoutCostAPIService provideTotalCheckoutCostAPIService(final WatchListCondenser watchListCondenser,
            final NonDiscountedWatchCostCalculator nonDiscountedWatchCostCalculator, final DiscountedWatchCostCalculator discountedWatchCostCalculator) {

        return new TotalCheckoutCostAPIService(watchListCondenser, nonDiscountedWatchCostCalculator, discountedWatchCostCalculator);
    }
}