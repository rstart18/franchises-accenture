package co.com.bancolombia.api.dto.request;

import lombok.Data;

@Data
public class BranchProductRequestDto {
    private String productName;
    private long branchId;
    private int stock;
}
