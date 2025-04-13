package co.com.bancolombia.r2dbc.repository;

import co.com.bancolombia.r2dbc.entities.BranchEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ReactiveBranchRepository extends ReactiveCrudRepository<BranchEntity, Long> {
    Mono<Boolean> existsByName(String name);
}
