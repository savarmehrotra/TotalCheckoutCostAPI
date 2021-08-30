package service;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import api.LoggingUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import constant.RequestResponseConstants;
import exception.ItemNotFoundException;
import model.TotalCheckoutCostAPIRequest;
import model.TotalCheckoutCostAPIResponse;
import model.WatchItem;
import org.apache.commons.lang3.tuple.ImmutablePair;

import processor.DiscountedWatchCostCalculator;
import processor.NonDiscountedWatchCostCalculator;
import processor.WatchListCondenser;

public class TotalCheckoutCostAPIService {

    private final WatchListCondenser watchListCondenser;
    private final NonDiscountedWatchCostCalculator nonDiscountedWatchCostCalculator;
    private final DiscountedWatchCostCalculator discountedWatchCostCalculator;

    @Inject
    public TotalCheckoutCostAPIService(final WatchListCondenser watchListCondenser,
            final NonDiscountedWatchCostCalculator nonDiscountedWatchCostCalculator,
            final DiscountedWatchCostCalculator discountedWatchCostCalculator) {

        this.watchListCondenser = watchListCondenser;
        this.nonDiscountedWatchCostCalculator = nonDiscountedWatchCostCalculator;
        this.discountedWatchCostCalculator = discountedWatchCostCalculator;
    }

    public TotalCheckoutCostAPIResponse processRequest(TotalCheckoutCostAPIRequest totalCheckoutCostAPIRequest) {

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put(RequestResponseConstants.CONTENT_TYPE_HEADER_KEY, RequestResponseConstants.CONTENT_TYPE_HEADER_VALUE);

        try {
            Gson gson = new GsonBuilder().setPrettyPrinting()
                    .create();

            LoggingUtil.log("Requests Received : " + gson.toJson(totalCheckoutCostAPIRequest));

            List<ImmutablePair<WatchItem, Integer>> watchItemCountList = watchListCondenser.getCondensedWatchItemList(
                    totalCheckoutCostAPIRequest.getWatchIDs());

            double totalCost = 0;
            for (ImmutablePair<WatchItem, Integer> watchItemAndCount : watchItemCountList) {

                totalCost += nonDiscountedWatchCostCalculator.getTotalCost(watchItemAndCount.getKey(), watchItemAndCount.getValue());
                LoggingUtil.log("Total Non Discounted Cost Received for watchId : {" + watchItemAndCount.getKey()
                        .getItemId() + "} with a purchase count of {" + watchItemAndCount.getValue() + "} is {" + totalCost +"}");

                totalCost += discountedWatchCostCalculator.getTotalCost(watchItemAndCount.getKey(), watchItemAndCount.getValue());

                LoggingUtil.log("Total Discounted Cost Received for watchId : {" + watchItemAndCount.getKey()
                        .getItemId() + "} with a purchase count of {" + watchItemAndCount.getValue() + "} is {" + totalCost + "}");
            }

            return TotalCheckoutCostAPIResponse.builder()
                    .statusCode(RequestResponseConstants.SUCCESS_CODE)
                    .headers(headers)
                    .body("price : " + totalCost)
                    .build();
        }
        catch (ItemNotFoundException ex) {

            return TotalCheckoutCostAPIResponse.builder()
                    .statusCode(RequestResponseConstants.ITEM_NOT_FOUND_CODE)
                    .headers(headers)
                    .body(ex.getMessage())
                    .build();
        }
        catch (Exception ex) {

            LoggingUtil.log("Error Processing Request due to " + ex.getCause());
            return TotalCheckoutCostAPIResponse.builder()
                    .statusCode(RequestResponseConstants.INTERNAL_SERVER_ERROR_CODE)
                    .headers(headers)
                    .body(RequestResponseConstants.INTERNAL_SERVER_ERROR_MESSAGE)
                    .build();
        }
    }
}