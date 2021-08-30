package dagger;

import javax.inject.Singleton;

import service.TotalCheckoutCostAPIService;

@Singleton
@Component(modules = { TotalCheckoutCostAPIServiceModule.class })
public interface TotalCheckoutCostAPIServiceComponent {

    TotalCheckoutCostAPIService getTotalCheckoutCostAPIService();

}