package co.com.bancolombia.usecase.business;

import co.com.bancolombia.exception.FranchiseException;
import co.com.bancolombia.gateways.api.BusinessRepository;
import co.com.bancolombia.gateways.spi.StorageRepository;
import co.com.bancolombia.model.Branch;
import co.com.bancolombia.model.BranchProduct;
import co.com.bancolombia.model.BranchProductInfo;
import co.com.bancolombia.model.Franchise;
import co.com.bancolombia.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public record BusinessUseCase(StorageRepository storageRepository) implements BusinessRepository {

    @Override
    public Mono<Franchise> createFranchise(Franchise franchise) {
        return storageRepository.existsFranchiseByName(franchise.getName())
                .flatMap(existsByName -> {
                    if (Boolean.TRUE.equals(existsByName)) {
                        return Mono.error(new FranchiseException.AlreadyExistsException("Franchise name already exists"));
                    }

                    return storageRepository.existsFranchiseByNit(franchise.getNit())
                            .flatMap(existsByNit -> {
                                if (Boolean.TRUE.equals(existsByNit)) {
                                    return Mono.error(new FranchiseException.AlreadyExistsException("Franchise NIT already exists"));
                                }

                                return storageRepository.saveFranchise(franchise);
                            });
                });
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

    @Override
    public Mono<Void> updateProductStock(Long branchId, Long productId, int stock) {
        return storageRepository.findActiveBranchProductByBranchIdAndProductId(branchId, productId)
                .switchIfEmpty(Mono.error(new FranchiseException.NotFoundException("Relation not found or is deleted")))
                .flatMap(bp -> {
                    bp.setStock(stock);
                    return storageRepository.saveBranchProduct(bp).then();
                });
    }

    @Override
    public Mono<Void> updateFranchiseName(Long franchiseId, String newName) {
        return storageRepository.existsFranchiseByName(newName)
                .flatMap(exists -> Boolean.TRUE.equals(exists)
                        ? Mono.error(new FranchiseException.AlreadyExistsException("Franchise name already exists"))
                        : storageRepository.findFranchiseById(franchiseId)
                        .switchIfEmpty(Mono.error(new FranchiseException.NotFoundException("Franchise not found")))
                        .flatMap(franchise -> {
                            franchise.setName(newName);
                            return storageRepository.saveFranchise(franchise).then();
                        })
                );
    }

    @Override
    public Mono<Void> updateBranchName(Long branchId, String newName) {
        return storageRepository.existsBranchByName(newName)
                .flatMap(exists -> Boolean.TRUE.equals(exists)
                        ? Mono.error(new FranchiseException.AlreadyExistsException("Branch name already exists"))
                        : storageRepository.findBranchById(branchId)
                        .switchIfEmpty(Mono.error(new FranchiseException.NotFoundException("Branch not found")))
                        .flatMap(branch -> {
                            branch.setName(newName);
                            return storageRepository.saveBranch(branch).then();
                        })
                );
    }

    @Override
    public Mono<Void> updateProductName(Long productId, String newName) {
        return storageRepository.existsProductByName(newName)
                .flatMap(exists -> Boolean.TRUE.equals(exists)
                        ? Mono.error(new FranchiseException.AlreadyExistsException("Product name already exists"))
                        : storageRepository.findProductById(productId)
                        .switchIfEmpty(Mono.error(new FranchiseException.NotFoundException("Product not found")))
                        .flatMap(product -> {
                            product.setName(newName);
                            return storageRepository.saveProduct(product).then();
                        }))
                .onErrorMap(e -> new FranchiseException.DBException("Error querying update product from DB: " + e.getMessage()));
    }

    @Override
    public Mono<Void> removeProductFromBranch(Long branchId, Long productId) {
        return storageRepository.findActiveBranchProductByBranchIdAndProductId(branchId, productId)
                .switchIfEmpty(Mono.error(new FranchiseException.NotFoundException("Branch-Product relation not found")))
                .flatMap(branchProduct -> {
                    if (branchProduct.getDeletedAt() != null) {
                        return Mono.error(new FranchiseException.AlreadyExistsException("Product already removed from this branch"));
                    }
                    branchProduct.setDeletedAt(java.time.LocalDateTime.now());
                    return storageRepository.saveBranchProduct(branchProduct).then();
                })
                .onErrorMap(e -> new FranchiseException.DBException("Error querying remove product from DB: " + e.getMessage()));
    }

    @Override
    public Flux<BranchProductInfo> findTopProductsByBranchForFranchise(Long franchiseId) {
        return storageRepository.findTopProductsByBranchForFranchise(franchiseId)
                .onErrorMap(e -> new FranchiseException.DBException("Error querying top products from DB: " + e.getMessage()));
    }
}
