package co.com.bancolombia.usecase.business;

import co.com.bancolombia.exception.FranchiseException;
import co.com.bancolombia.gateways.api.FranchiseRepository;
import co.com.bancolombia.gateways.spi.StorageRepository;
import co.com.bancolombia.model.Branch;
import co.com.bancolombia.model.BranchProduct;
import co.com.bancolombia.model.Franchise;
import co.com.bancolombia.model.Product;
import reactor.core.publisher.Mono;

public record BusinessUseCase(StorageRepository storageRepository) implements FranchiseRepository {

    @Override
    public Mono<Franchise> createFranchise(Franchise franchise) {
        return storageRepository.existsFranchiseByName(franchise.getName())
                .flatMap(exists ->
                        Boolean.TRUE.equals(exists)
                                ? Mono.error(new FranchiseException.AlreadyExistsException("Franchise name already exists"))
                                : storageRepository.saveFranchise(franchise)
                );
    }

    @Override
    public Mono<Branch> createBranch(Branch branch) {
        return storageRepository.existsBranchByName(branch.getName())
                .flatMap(exists ->
                        Boolean.TRUE.equals(exists)
                                ? Mono.error(new FranchiseException.AlreadyExistsException("Branch name already exists"))
                                : storageRepository.saveBranch(branch)
                );
    }


    @Override
    public Mono<BranchProduct> createBranchProduct(BranchProduct branchProduct) {
        return this.storageRepository.saveBranchProduct(branchProduct);
    }

    @Override
    public Mono<BranchProduct> addProductToBranch(String productName, Long branchId, int stock) {
        return storageRepository.findProductByName(productName)
                .switchIfEmpty(
                        storageRepository.saveProduct(Product.builder()
                                .name(productName)
                                .build()
                        )
                )
                .flatMap(product ->
                        storageRepository.existsBranchProduct(branchId, product.getId())
                                .flatMap(exists ->
                                        Boolean.TRUE.equals(exists)
                                                ? Mono.error(new FranchiseException.AlreadyExistsException("Product already assigned to this branch"))
                                                : storageRepository.saveBranchProduct(
                                                BranchProduct.builder()
                                                        .branchId(branchId)
                                                        .productId(product.getId())
                                                        .stock(stock)
                                                        .build()
                                        )
                                )
                );
    }


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
