package co.com.bancolombia.r2dbc.adapters;

import co.com.bancolombia.gateways.spi.StorageRepository;
import co.com.bancolombia.model.Branch;
import co.com.bancolombia.model.BranchProduct;
import co.com.bancolombia.model.BranchProductInfo;
import co.com.bancolombia.model.Franchise;
import co.com.bancolombia.model.Product;
import co.com.bancolombia.r2dbc.mapper.ProductMapper2;
import co.com.bancolombia.r2dbc.repository.ReactiveBranchProductRepository;
import co.com.bancolombia.r2dbc.repository.ReactiveBranchRepository;
import co.com.bancolombia.r2dbc.repository.ReactiveFranchiseRepository;
import co.com.bancolombia.r2dbc.repository.ReactiveProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MyReactiveRepositoryAdapter implements StorageRepository {
    private final ProductMapper2 productMapper2;
    private final ReactiveProductRepository reactiveProductRepository;
    private final ReactiveBranchProductRepository reactiveBranchProductRepository;
    private final ReactiveFranchiseRepository reactiveFranchiseRepository;
    private final ReactiveBranchRepository reactiveBranchRepository;

    @Override
    public Mono<Franchise> saveFranchise(Franchise franchise) {
        return this.reactiveFranchiseRepository.save(this.productMapper2.toEntity(franchise))
                .map(productMapper2::toModel);
    }

    @Override
    public Mono<Branch> saveBranch(Branch branch) {
        return this.reactiveBranchRepository.save(this.productMapper2.toEntity(branch))
                .map(productMapper2::toModel);
    }

    @Override
    public Mono<BranchProduct> saveBranchProduct(BranchProduct branchProduct) {
        return this.reactiveBranchProductRepository.save(this.productMapper2.toEntity(branchProduct))
                .map(productMapper2::toModel);
    }

    @Override
    public Mono<Product> saveProduct(Product product) {
        return this.reactiveProductRepository.save(this.productMapper2.toEntity(product))
                .map(productMapper2::toModel);
    }

    @Override
    public Mono<Boolean> existsFranchiseByName(String name) {
        return this.reactiveFranchiseRepository.existsByName(name);
    }

    @Override
    public Mono<Boolean> existsBranchByName(String name) {
        return this.reactiveBranchRepository.existsByName(name);
    }

    @Override
    public Mono<Boolean> existsProductByName(String name) {
        return this.reactiveProductRepository.existsByName(name);
    }

    @Override
    public Mono<Product> findProductByName(String productName) {
        return this.reactiveProductRepository.findByName(productName)
                .map(productMapper2::toModel);
    }

    @Override
    public Mono<Franchise> findFranchiseById(long franchiseId) {
        return this.reactiveFranchiseRepository.findById(franchiseId)
                .map(productMapper2::toModel);
    }

    public Mono<Branch> findBranchById(long branchId) {
        return this.reactiveBranchRepository.findById(branchId)
                .map(productMapper2::toModel);
    }

    @Override
    public Mono<Product> findProductById(long productId) {
        return this.reactiveProductRepository.findById(productId)
                .map(productMapper2::toModel);
    }

    @Override
    public Mono<Boolean> existsFranchiseByNit(String nit) {
        return this.reactiveFranchiseRepository.existsByNit(nit);
    }

    @Override
    public Mono<Boolean> existsBranchProduct(long branchId, long productId) {
        return this.reactiveBranchProductRepository.existsByBranchIdAndProductId(branchId, productId);
    }

    @Override
    public Mono<BranchProduct> findActiveBranchProductByBranchIdAndProductId(long branchId, long productId) {
        return this.reactiveBranchProductRepository.findByBranchIdAndProductIdAndDeletedAtIsNull(branchId, productId)
                .map(productMapper2::toModel);
    }

    @Override
    public Flux<BranchProductInfo> findTopProductsByBranchForFranchise(Long franchiseId) {
        return this.reactiveBranchProductRepository.findTopProductsByBranchForFranchise(franchiseId);
    }
}
