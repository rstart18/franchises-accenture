package co.com.bancolombia.r2dbc.repository;

import co.com.bancolombia.r2dbc.entities.BranchProductEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ReactiveBranchProductRepository extends ReactiveCrudRepository<BranchProductEntity, Long> {
    Mono<Boolean> existsByBranchIdAndProductId(Long branchId, Long productId);
}
