package co.com.bancolombia.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchProduct {
    private Long id;
    private Long branchId;
    private Long productId;
    private int stock;
    private LocalDateTime deletedAt;
}
