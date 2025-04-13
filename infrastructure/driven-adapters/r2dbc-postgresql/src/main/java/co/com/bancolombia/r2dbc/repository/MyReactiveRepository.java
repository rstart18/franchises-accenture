package co.com.bancolombia.r2dbc.repository;

import co.com.bancolombia.r2dbc.entities.ProductEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MyReactiveRepository extends ReactiveCrudRepository<ProductEntity, Long> {
    Mono<Boolean> existsByName(String name);
}
