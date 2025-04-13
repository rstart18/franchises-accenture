package co.com.bancolombia.usecase.business;

import co.com.bancolombia.exception.FranchiseException;
import co.com.bancolombia.gateways.api.FranchiseRepository;
import co.com.bancolombia.gateways.spi.StorageRepository;
import co.com.bancolombia.model.Product;
import reactor.core.publisher.Mono;

public record BusinessUseCase(StorageRepository storageRepository) implements FranchiseRepository {
    @Override
    public Mono<Product> createProduct(Product product) {
        return this.storageRepository.existsProductByName(product.getName())
                .flatMap(exists ->
                        Boolean.TRUE.equals(exists)
                                ? Mono.error(new FranchiseException.AlreadyExistsException("Product name already exists"))
                                : this.storageRepository.saveProduct(product)
                );
    }
}
