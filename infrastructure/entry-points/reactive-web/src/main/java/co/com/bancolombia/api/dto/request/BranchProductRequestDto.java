package co.com.bancolombia.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BranchProductRequestDto {
    private String productName;
    private long branchId;
    private int stock;
}
