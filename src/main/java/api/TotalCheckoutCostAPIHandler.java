package api;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import constant.RequestResponseConstants;
import dagger.DaggerTotalCheckoutCostAPIServiceComponent;
import dagger.TotalCheckoutCostAPIServiceComponent;
import model.TotalCheckoutCostAPIRequest;
import model.TotalCheckoutCostAPIResponse;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.Objects;

/**
 * @author : Savar Mehrotra
 *
 * 1)Entry point for the API Service.
 *
 * 2)Receives the JSON data in the HTTP request and passes the response back received from the API service.
 *
 * 3)Intializes object creation of the API Service and respective dependencies leverging Dagger's Dependency Injection.
 *
 * 4)Checks is the input in the correct format and returns an HTTP error code of 400 with an "Incorrect Input Message" to notify the client.
 *
 * */

//TODO : Throttling
    //TODO : Email above and beyond. Display knowledge Large sacle systems
public class TotalCheckoutCostAPIHandler implements RequestHandler<JSONObject, TotalCheckoutCostAPIResponse> {

    private TotalCheckoutCostAPIServiceComponent totalCheckoutCostAPIServiceComponent;

    @Override
    public TotalCheckoutCostAPIResponse handleRequest(final JSONObject request, Context context) {

        initialize();
        ObjectMapper objectMapper = new ObjectMapper();
        TotalCheckoutCostAPIRequest totalCheckoutCostAPIRequest;

        try {
            totalCheckoutCostAPIRequest = objectMapper.readValue(request.get("body")
                    .toString(), TotalCheckoutCostAPIRequest.class);
        }
        catch (IOException e) {

            return TotalCheckoutCostAPIResponse.builder()
                    .body(RequestResponseConstants.INCORRECT_INPUT_PASSED_MESSAGE)
                    .statusCode(RequestResponseConstants.INCORRECT_INPUT_ERROR_CODE)
                    .build();
        }

        return totalCheckoutCostAPIServiceComponent.getTotalCheckoutCostAPIService()
                .processRequest(totalCheckoutCostAPIRequest);
    }

    private void initialize() {

        this.totalCheckoutCostAPIServiceComponent = getTotalCheckoutCostAPIServiceComponent();
    }

    private TotalCheckoutCostAPIServiceComponent getTotalCheckoutCostAPIServiceComponent() {

        if (Objects.isNull(totalCheckoutCostAPIServiceComponent)) {

            return DaggerTotalCheckoutCostAPIServiceComponent.create();
        }
        return totalCheckoutCostAPIServiceComponent;
    }
}