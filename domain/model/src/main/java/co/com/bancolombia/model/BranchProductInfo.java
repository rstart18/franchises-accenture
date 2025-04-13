package co.com.bancolombia.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchProductInfo {
    private Long branchId;
    private String branchName;
    private Long productId;
    private String productName;
    private int stock;
}
