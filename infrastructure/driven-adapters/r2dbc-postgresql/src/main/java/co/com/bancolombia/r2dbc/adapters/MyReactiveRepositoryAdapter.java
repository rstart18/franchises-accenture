package co.com.bancolombia.r2dbc.adapters;

import co.com.bancolombia.gateways.spi.StorageRepository;
import co.com.bancolombia.model.Branch;
import co.com.bancolombia.model.Franchise;
import co.com.bancolombia.model.Product;
import co.com.bancolombia.r2dbc.mapper.ProductMapper2;
import co.com.bancolombia.r2dbc.repository.MyReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MyReactiveRepositoryAdapter implements StorageRepository {
    private final ProductMapper2 productMapper2;
    private final MyReactiveRepository myReactiveRepository;

    @Override
    public Mono<Franchise> saveFranchise(Franchise franchise) {
        return null;
    }

    @Override
    public Mono<Branch> saveBranch(Branch branch) {
        return null;
    }

    @Override
    public Mono<Product> saveProduct(Product product) {
        return this.myReactiveRepository.save(this.productMapper2.toEntity(product))
                .map(productMapper2::toModel);
    }

    @Override
    public Mono<Boolean> existsProductByName(String name) {
        return this.myReactiveRepository.existsByName(name);
    }
}
