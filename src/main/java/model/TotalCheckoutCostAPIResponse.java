package model;

import java.io.Serializable;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * POJO to map output response to be sent to the client
 *
 * Status Code :
 * 1)400 : Incorrect Input
 * 2)404 : Requested Item Not Found in Catalog
 * 3)500 : Internal Server Error
 *
 * Body : Contains the final price at checkout
 * Example : {"price" : 360}
 * */

@Builder
@Data
@AllArgsConstructor
public class TotalCheckoutCostAPIResponse implements Serializable, Cloneable {

    private int statusCode;
    private Map<String, String> headers;
    private String body;
}