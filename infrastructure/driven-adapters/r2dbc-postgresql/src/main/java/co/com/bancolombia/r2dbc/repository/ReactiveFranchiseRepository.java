package co.com.bancolombia.r2dbc.repository;

import co.com.bancolombia.r2dbc.entities.FranchiseEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ReactiveFranchiseRepository extends ReactiveCrudRepository<FranchiseEntity, Long> {
    Mono<Boolean> existsByName(String name);
}