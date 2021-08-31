import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.amazonaws.AmazonServiceException;
import database.CatalogTableMock;
import database.constant.CatalogTableConstants;
import doa.WatchCatalogueDAO;
import exception.DataBaseException;
import model.TotalCheckoutCostAPIRequest;
import model.TotalCheckoutCostAPIResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import processor.DiscountedWatchCostCalculator;
import processor.NonDiscountedWatchCostCalculator;
import processor.WatchListCondenser;
import service.TotalCheckoutCostAPIService;

import java.util.Arrays;

public class TotalCheckoutCostAPITest {

    @Mock
    private WatchCatalogueDAO watchCatalogueDAO;

    private WatchListCondenser watchListCondenser;
    private NonDiscountedWatchCostCalculator nonDiscountedWatchCostCalculator;
    private DiscountedWatchCostCalculator discountedWatchCostCalculator;
    private TotalCheckoutCostAPIService totalCheckoutCostAPIService;

    @BeforeEach
    public void setUpMocks()
            throws DataBaseException {

        MockitoAnnotations.initMocks(this);

        creatSampleCatalogDB();

        watchListCondenser = new WatchListCondenser(watchCatalogueDAO);
        nonDiscountedWatchCostCalculator = new NonDiscountedWatchCostCalculator();
        discountedWatchCostCalculator = new DiscountedWatchCostCalculator();
        totalCheckoutCostAPIService = new TotalCheckoutCostAPIService(watchListCondenser, nonDiscountedWatchCostCalculator,
                discountedWatchCostCalculator);
    }

    @Test
    void WHEN_HandleRequestIsInvokedWithValidInputs_ToTestDiscountedCostCalculator_THEN_ItReturns_ExpectedValue() {

        TotalCheckoutCostAPIRequest totalCheckoutCostAPIRequest = TotalCheckoutCostAPIRequest.builder()
                .watchIDs(Arrays.asList(CatalogTableConstants.SAMPLE_ITEM_ID_1, CatalogTableConstants.SAMPLE_ITEM_ID_1,
                        CatalogTableConstants.SAMPLE_ITEM_ID_1, CatalogTableConstants.SAMPLE_ITEM_ID_1, CatalogTableConstants.SAMPLE_ITEM_ID_2,
                        CatalogTableConstants.SAMPLE_ITEM_ID_2, CatalogTableConstants.SAMPLE_ITEM_ID_2))
                .build();

        TotalCheckoutCostAPIResponse totalCheckoutCostAPIResponse = totalCheckoutCostAPIService.processRequest(totalCheckoutCostAPIRequest);
        assertEquals("price :" + 500.0, totalCheckoutCostAPIResponse.getBody());
    }

    @Test
    void WHEN_HandleRequestIsInvokedWithValidInputs_ToTestNonDiscountedCostCalculator_THEN_ItReturns_ExpectedValue() {

        TotalCheckoutCostAPIRequest totalCheckoutCostAPIRequest = TotalCheckoutCostAPIRequest.builder()
                .watchIDs(Arrays.asList(CatalogTableConstants.SAMPLE_ITEM_ID_1, CatalogTableConstants.SAMPLE_ITEM_ID_1,
                        CatalogTableConstants.SAMPLE_ITEM_ID_2, CatalogTableConstants.SAMPLE_ITEM_ID_3, CatalogTableConstants.SAMPLE_ITEM_ID_4))
                .build();

        TotalCheckoutCostAPIResponse totalCheckoutCostAPIResponse = totalCheckoutCostAPIService.processRequest(totalCheckoutCostAPIRequest);
        assertEquals("price :" + 360.0, totalCheckoutCostAPIResponse.getBody());
    }

    @Test
    void WHEN_HandleRequestIsInvokedWithValidInputs_NotPresentInCatalog_THEN_ItThrows_Returns_NotFoundResponseWithItsItemid() {

        TotalCheckoutCostAPIRequest totalCheckoutCostAPIRequest = TotalCheckoutCostAPIRequest.builder()
                .watchIDs(Arrays.asList(CatalogTableConstants.SAMPLE_ITEM_ID_1, CatalogTableConstants.SAMPLE_ITEM_ID_1,
                        CatalogTableConstants.SAMPLE_MISSING_ITEM_ID))
                .build();

        TotalCheckoutCostAPIResponse totalCheckoutCostAPIResponse = totalCheckoutCostAPIService.processRequest(totalCheckoutCostAPIRequest);
        assertEquals(CatalogTableConstants.ITEM_NOT_FOUND_ERROR_MESSAGE, totalCheckoutCostAPIResponse.getBody());
        assertEquals(404, totalCheckoutCostAPIResponse.getStatusCode());
    }

    @Test
    void WHEN_HandleRequestIsInvokedWithInputs_AND_AWSFails_THEN_ItThrows_Returns_InternalServerResponse()
            throws DataBaseException {

        TotalCheckoutCostAPIRequest totalCheckoutCostAPIRequest = TotalCheckoutCostAPIRequest.builder()
                .watchIDs(Arrays.asList(CatalogTableConstants.SAMPLE_ITEM_ID_1, CatalogTableConstants.SAMPLE_ITEM_ID_1))
                .build();

        when(watchCatalogueDAO.getProductData(CatalogTableConstants.SAMPLE_ITEM_ID_1)).thenThrow(AmazonServiceException.class);
        TotalCheckoutCostAPIResponse totalCheckoutCostAPIResponse = totalCheckoutCostAPIService.processRequest(totalCheckoutCostAPIRequest);
        assertEquals("Internal Server Error", totalCheckoutCostAPIResponse.getBody());
        assertEquals(500, totalCheckoutCostAPIResponse.getStatusCode());
    }

    private void creatSampleCatalogDB()
            throws DataBaseException {

        when(watchCatalogueDAO.getProductData(CatalogTableConstants.SAMPLE_ITEM_ID_1)).thenReturn(
                CatalogTableMock.getItem(CatalogTableConstants.SAMPLE_ITEM_ID_1));
        when(watchCatalogueDAO.getProductData(CatalogTableConstants.SAMPLE_ITEM_ID_2)).thenReturn(
                CatalogTableMock.getItem(CatalogTableConstants.SAMPLE_ITEM_ID_2));
        when(watchCatalogueDAO.getProductData(CatalogTableConstants.SAMPLE_ITEM_ID_3)).thenReturn(
                CatalogTableMock.getItem(CatalogTableConstants.SAMPLE_ITEM_ID_3));
        when(watchCatalogueDAO.getProductData(CatalogTableConstants.SAMPLE_ITEM_ID_4)).thenReturn(
                CatalogTableMock.getItem(CatalogTableConstants.SAMPLE_ITEM_ID_4));
        when(watchCatalogueDAO.getProductData(CatalogTableConstants.SAMPLE_MISSING_ITEM_ID)).thenReturn(
                CatalogTableMock.getItem(CatalogTableConstants.SAMPLE_MISSING_ITEM_ID));
    }
}
