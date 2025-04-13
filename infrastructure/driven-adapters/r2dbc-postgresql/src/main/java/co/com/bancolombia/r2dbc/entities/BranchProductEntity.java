package co.com.bancolombia.r2dbc.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("branch_product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BranchProductEntity {
    @Id
    private Long id;

    @Column("branch_id")
    private Long branchId;

    @Column("product_id")
    private Long productId;

    @Column("stock")
    private int stock;
}
