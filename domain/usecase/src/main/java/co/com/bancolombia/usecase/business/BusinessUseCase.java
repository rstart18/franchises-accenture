package co.com.bancolombia.usecase.business;

import co.com.bancolombia.model.Product;
import co.com.bancolombia.gateways.api.FranchiseRepository;
import co.com.bancolombia.gateways.spi.StorageRepository;
import reactor.core.publisher.Mono;

public record BusinessUseCase(StorageRepository storageRepository) implements FranchiseRepository {
    @Override
    public Mono<Product> createProduct(Product product) {
        return this.storageRepository.saveProduct(product);
    }
}
