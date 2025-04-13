package co.com.bancolombia.gateways.api;

import co.com.bancolombia.model.Branch;
import co.com.bancolombia.model.BranchProduct;
import co.com.bancolombia.model.Franchise;
import co.com.bancolombia.model.Product;
import reactor.core.publisher.Mono;


public interface FranchiseRepository {
    Mono<Franchise> createFranchise(Franchise franchise);

    Mono<Branch> createBranch(Branch branch);

    Mono<Product> createProduct(Product product);

    Mono<BranchProduct> createBranchProduct(BranchProduct branchProduct);

    Mono<BranchProduct> addProductToBranch(String productName, Long branchId, int stock);

}
