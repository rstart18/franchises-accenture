package co.com.bancolombia.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TopProductPerBranchResponseDto {
    private Long branchId;
    private String branchName;
    private Long productId;
    private String productName;
    private int stock;
}
