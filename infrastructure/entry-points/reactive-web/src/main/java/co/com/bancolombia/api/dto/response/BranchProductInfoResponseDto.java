package co.com.bancolombia.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchProductInfoResponseDto {
    private Long branchId;
    private String branchName;
    private Long productId;
    private String productName;
    private int stock;
}
