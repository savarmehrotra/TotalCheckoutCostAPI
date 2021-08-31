package model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO to map input request of the list of watchIds received in the JSON request
 * */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalCheckoutCostAPIRequest{

    private List<String> watchIDs;

}