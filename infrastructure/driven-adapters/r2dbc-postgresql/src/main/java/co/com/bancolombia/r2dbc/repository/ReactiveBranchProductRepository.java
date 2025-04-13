package co.com.bancolombia.r2dbc.repository;

import co.com.bancolombia.model.BranchProductInfo;
import co.com.bancolombia.r2dbc.entities.BranchProductEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ReactiveBranchProductRepository extends ReactiveCrudRepository<BranchProductEntity, Long> {
    Mono<Boolean> existsByBranchIdAndProductId(Long branchId, Long productId);

    Mono<BranchProductEntity> findByBranchIdAndProductIdAndDeletedAtIsNull(Long branchId, Long productId);

    @Query("""
                SELECT DISTINCT ON (bp.branch_id)
                    bp.branch_id AS branch_id,
                    b.name AS branch_name,
                    bp.product_id AS product_id,
                    p.name AS product_name,
                    bp.stock AS stock
                FROM branch_product bp
                JOIN branch b ON bp.branch_id = b.id
                JOIN product p ON bp.product_id = p.id
                WHERE b.franchise_id = :franchiseId
                  AND bp.deleted_at IS NULL
                ORDER BY bp.branch_id, bp.stock DESC
            """)
    Flux<BranchProductInfo> findTopProductsByBranchForFranchise(Long franchiseId);
}
