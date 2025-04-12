package co.com.bancolombia.api.mapper;

import co.com.bancolombia.api.dto.request.ProductRequestDto;
import co.com.bancolombia.api.dto.response.ProductResponseDto;
import co.com.bancolombia.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toModel(ProductRequestDto productRequest);
    ProductResponseDto toDto(Product product);
}
