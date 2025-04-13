package co.com.bancolombia.gateways.spi;

import co.com.bancolombia.model.Branch;
import co.com.bancolombia.model.Franchise;
import co.com.bancolombia.model.Product;
import reactor.core.publisher.Mono;

public interface StorageRepository {
    Mono<Franchise> saveFranchise(Franchise franchise);

    Mono<Branch> saveBranch(Branch branch);

    Mono<Product> saveProduct(Product product);

    Mono<Boolean> existsProductByName(String name);
}
