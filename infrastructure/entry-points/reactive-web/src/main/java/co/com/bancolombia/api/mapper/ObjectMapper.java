package co.com.bancolombia.api.mapper;

import co.com.bancolombia.api.dto.request.BranchProductRequestDto;
import co.com.bancolombia.api.dto.request.BranchRequestDto;
import co.com.bancolombia.api.dto.request.FranchiseRequestDto;
import co.com.bancolombia.api.dto.request.ProductRequestDto;
import co.com.bancolombia.api.dto.response.BranchProductResponseDto;
import co.com.bancolombia.api.dto.response.BranchResponseDto;
import co.com.bancolombia.api.dto.response.FranchiseResponseDto;
import co.com.bancolombia.api.dto.response.ProductResponseDto;
import co.com.bancolombia.model.Branch;
import co.com.bancolombia.model.BranchProduct;
import co.com.bancolombia.model.Franchise;
import co.com.bancolombia.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ObjectMapper {
    Franchise toModel(FranchiseRequestDto franchiseRequestDto);

    FranchiseResponseDto toDto(Franchise franchise);

    Branch toModel(BranchRequestDto branchRequestDto);

    BranchResponseDto toDto(Branch branch);

    Product toModel(ProductRequestDto productRequest);

    ProductResponseDto toDto(Product product);

    BranchProduct toModel(BranchProductRequestDto productRequestDto);

    BranchProductResponseDto toDto(BranchProduct branchProduct);
}
