package co.com.bancolombia.gateways.api;

import co.com.bancolombia.model.Product;
import reactor.core.publisher.Mono;


public interface FranchiseRepository {
    Mono<Product> createProduct(Product product);
}
