package co.com.bancolombia.r2dbc.mapper;

import co.com.bancolombia.model.Product;
import co.com.bancolombia.r2dbc.entities.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper2 {
    Product toModel(ProductEntity productEntity);
    ProductEntity toEntity(Product product);
}
