package co.com.bancolombia.api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopProductPerBranchResponseDto {
    private Long branchId;
    private String branchName;
    private Long productId;
    private String productName;
    private int stock;
}
