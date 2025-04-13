package co.com.bancolombia.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BranchProductResponseDto {
    private Long id;
    private Long branchId;
    private Long productId;
    private int stock;
}
