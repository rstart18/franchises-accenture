package co.com.bancolombia.gateways.api;

import co.com.bancolombia.model.Branch;
import co.com.bancolombia.model.BranchProduct;
import co.com.bancolombia.model.BranchProductInfo;
import co.com.bancolombia.model.Franchise;
import co.com.bancolombia.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface BusinessRepository {
    Mono<Franchise> createFranchise(Franchise franchise);

    Mono<Branch> createBranch(Branch branch);

    Mono<Product> createProduct(Product product);

    Mono<BranchProduct> createBranchProduct(BranchProduct branchProduct);

    Mono<BranchProduct> addProductToBranch(String productName, Long branchId, int stock);

    Mono<Void> updateProductStock(Long branchId, Long productId, int stock);

    Mono<Void> removeProductFromBranch(Long branchId, Long productId);

    Flux<BranchProductInfo> findTopProductsByBranchForFranchise(Long franchiseId);

    Mono<Void> updateFranchiseName(Long franchiseId, String newName);

    Mono<Void> updateBranchName(Long branchId, String newName);

    Mono<Void> updateProductName(Long productId, String newName);
}
