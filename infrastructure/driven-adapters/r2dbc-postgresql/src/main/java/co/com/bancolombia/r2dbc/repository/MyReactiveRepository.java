package co.com.bancolombia.r2dbc.repository;

import co.com.bancolombia.r2dbc.entities.ProductEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyReactiveRepository extends ReactiveCrudRepository<ProductEntity, Long> {

}
