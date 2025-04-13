package co.com.bancolombia.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchProductInfo {
    private Long branchId;
    private String branchName;
    private Long productId;
    private String productName;
    private int stock;
}
