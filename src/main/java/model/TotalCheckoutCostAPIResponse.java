package model;

import java.io.Serializable;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class TotalCheckoutCostAPIResponse implements Serializable, Cloneable {

    private int statusCode;
    private Map<String, String> headers;
    private String body;
}