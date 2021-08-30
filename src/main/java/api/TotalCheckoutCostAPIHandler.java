package api;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import dagger.DaggerTotalCheckoutCostAPIServiceComponent;
import dagger.TotalCheckoutCostAPIServiceComponent;
import model.TotalCheckoutCostAPIRequest;
import model.TotalCheckoutCostAPIResponse;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.Objects;


//TODO : Convert floating strings to Constants.
// TODO : Add JavaDoc
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
                    .body("Incorrect Inputs Passed")
                    .statusCode(400)
                    .build();
        }

        return totalCheckoutCostAPIServiceComponent.getTotalCheckoutCostAPIService()
                .processRequest(totalCheckoutCostAPIRequest, context.getLogger());
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