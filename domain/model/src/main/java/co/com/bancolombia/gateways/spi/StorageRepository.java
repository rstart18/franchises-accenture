package co.com.bancolombia.gateways.spi;

import co.com.bancolombia.model.Branch;
import co.com.bancolombia.model.BranchProduct;
import co.com.bancolombia.model.BranchProductInfo;
import co.com.bancolombia.model.Franchise;
import co.com.bancolombia.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StorageRepository {
    Mono<Franchise> saveFranchise(Franchise franchise);

    Mono<Branch> saveBranch(Branch branch);

    public Mono<BranchProduct> saveBranchProduct(BranchProduct branchProduct);

    Mono<Product> saveProduct(Product product);

    Mono<Boolean> existsFranchiseByName(String name);

    Mono<Boolean> existsBranchByName(String name);

    Mono<Boolean> existsProductByName(String name);

    Mono<Product> findProductByName(String productName);

    Mono<Boolean> existsBranchProduct(long branchId, long productId);

    Mono<BranchProduct> findActiveBranchProductByBranchIdAndProductId(long branchId, long profuctId);

    Flux<BranchProductInfo> findTopProductsByBranchForFranchise(Long franchiseId);
}
