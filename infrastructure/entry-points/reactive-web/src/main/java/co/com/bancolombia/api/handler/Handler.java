package co.com.bancolombia.api.handler;

import co.com.bancolombia.api.dto.request.BranchProductRequestDto;
import co.com.bancolombia.api.dto.request.BranchRequestDto;
import co.com.bancolombia.api.dto.request.FranchiseRequestDto;
import co.com.bancolombia.api.dto.request.ProductRequestDto;
import co.com.bancolombia.api.dto.request.UpdateBranchNameRequestDto;
import co.com.bancolombia.api.dto.request.UpdateFranchiseNameRequestDto;
import co.com.bancolombia.api.dto.request.UpdateProductNameRequestDto;
import co.com.bancolombia.api.dto.request.UpdateStockRequestDto;
import co.com.bancolombia.api.dto.response.ApiResponse;
import co.com.bancolombia.api.dto.response.BranchProductResponseDto;
import co.com.bancolombia.api.dto.response.BranchResponseDto;
import co.com.bancolombia.api.dto.response.FranchiseResponseDto;
import co.com.bancolombia.api.dto.response.ProductResponseDto;
import co.com.bancolombia.api.mapper.ObjectMapper;
import co.com.bancolombia.gateways.api.BusinessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Handler implements IHandler {
    private final ObjectMapper objectMapper;
    private final BusinessRepository businessRepository;

    public Mono<ServerResponse> createFranchise(ServerRequest request) {
        return request.bodyToMono(FranchiseRequestDto.class)
                .flatMap(franchiseRequestDto ->
                        this.businessRepository.createFranchise(this.objectMapper.toModel(franchiseRequestDto))
                                .map(this.objectMapper::toDto)
                                .flatMap(franchiseResponseDto ->
                                        ServerResponse.status(HttpStatus.CREATED)
                                                .bodyValue(ApiResponse.<FranchiseResponseDto>builder()
                                                        .data(List.of(franchiseResponseDto))
                                                        .build())));
    }

    @Override
    public Mono<ServerResponse> createBranch(ServerRequest request) {
        return request.bodyToMono(BranchRequestDto.class)
                .flatMap(branchRequestDto ->
                        businessRepository.createBranch(this.objectMapper.toModel(branchRequestDto))
                                .map(this.objectMapper::toDto)
                                .flatMap(branchResponseDto ->
                                        ServerResponse.status(HttpStatus.CREATED)
                                                .bodyValue(ApiResponse.<BranchResponseDto>builder()
                                                        .data(List.of(branchResponseDto))
                                                        .build())));
    }


    public Mono<ServerResponse> addProductToBranch(ServerRequest request) {
        return request.bodyToMono(BranchProductRequestDto.class)
                .flatMap(dto ->
                        businessRepository.addProductToBranch(dto.getProductName(), dto.getBranchId(), dto.getStock())
                                .map(objectMapper::toDto)
                                .flatMap(branchProductResponseDto ->
                                        ServerResponse.status(HttpStatus.CREATED)
                                                .bodyValue(ApiResponse.<BranchProductResponseDto>builder()
                                                        .data(List.of(branchProductResponseDto))
                                                        .build())));
    }


    @Override
    public Mono<ServerResponse> createProduct(ServerRequest request) {
        return request.bodyToMono(ProductRequestDto.class)
                .flatMap(productRequestDto ->
                        this.businessRepository.createProduct(this.objectMapper.toModel(productRequestDto))
                                .map(this.objectMapper::toDto)
                                .flatMap(productResponseDto ->
                                        ServerResponse.status(HttpStatus.CREATED)
                                                .bodyValue(ApiResponse.<ProductResponseDto>builder()
                                                        .data(List.of(productResponseDto))
                                                        .build())));
    }

    public Mono<ServerResponse> updateProductStock(ServerRequest request) {
        Long branchId = Long.valueOf(request.pathVariable("branchId"));
        Long productId = Long.valueOf(request.pathVariable("productId"));

        return request.bodyToMono(UpdateStockRequestDto.class)
                .flatMap(dto ->
                        businessRepository.updateProductStock(branchId, productId, dto.getStock())
                )
                .then(ServerResponse.noContent().build());
    }


    public Mono<ServerResponse> removeProductFromBranch(ServerRequest request) {
        Long branchId = Long.valueOf(request.pathVariable("branchId"));
        Long productId = Long.valueOf(request.pathVariable("productId"));

        return businessRepository.removeProductFromBranch(branchId, productId)
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> getTopProductsByBranch(ServerRequest request) {
        Long franchiseId = Long.valueOf(request.pathVariable("franchiseId"));

        return businessRepository.findTopProductsByBranchForFranchise(franchiseId)
                .collectList()
                .flatMap(list -> ServerResponse.ok().bodyValue(list));
    }

    public Mono<ServerResponse> updateFranchiseName(ServerRequest request) {
        Long franchiseId = Long.valueOf(request.pathVariable("franchiseId"));

        return request.bodyToMono(UpdateFranchiseNameRequestDto.class)
                .flatMap(dto -> businessRepository.updateFranchiseName(franchiseId, dto.getName()))
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> updateBranchName(ServerRequest request) {
        Long branchId = Long.valueOf(request.pathVariable("branchId"));

        return request.bodyToMono(UpdateBranchNameRequestDto.class)
                .flatMap(dto -> businessRepository.updateBranchName(branchId, dto.getName()))
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> updateProductName(ServerRequest request) {
        Long productId = Long.valueOf(request.pathVariable("productId"));

        return request.bodyToMono(UpdateProductNameRequestDto.class)
                .flatMap(dto -> businessRepository.updateProductName(productId, dto.getName()))
                .then(ServerResponse.noContent().build());
    }
}
