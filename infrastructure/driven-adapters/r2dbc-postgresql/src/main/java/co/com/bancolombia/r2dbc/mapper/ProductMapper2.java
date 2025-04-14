package co.com.bancolombia.r2dbc.mapper;

import co.com.bancolombia.model.Branch;
import co.com.bancolombia.model.BranchProduct;
import co.com.bancolombia.model.Franchise;
import co.com.bancolombia.model.Product;
import co.com.bancolombia.r2dbc.entities.BranchEntity;
import co.com.bancolombia.r2dbc.entities.BranchProductEntity;
import co.com.bancolombia.r2dbc.entities.FranchiseEntity;
import co.com.bancolombia.r2dbc.entities.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper2 {
    @Mapping(target = "branches", ignore = true)
    Franchise toModel(FranchiseEntity franchiseEntity);

    FranchiseEntity toEntity(Franchise franchise);

    @Mapping(target = "products", ignore = true)
    Branch toModel(BranchEntity branch);

    BranchEntity toEntity(Branch branch);

    Product toModel(ProductEntity productEntity);

    ProductEntity toEntity(Product product);

    BranchProduct toModel(BranchProductEntity branchProductEntity);

    BranchProductEntity toEntity(BranchProduct branchProduct);
}
