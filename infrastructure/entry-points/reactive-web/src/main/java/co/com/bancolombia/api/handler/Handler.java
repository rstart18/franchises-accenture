package co.com.bancolombia.api.handler;

import co.com.bancolombia.api.dto.request.BranchProductRequestDto;
import co.com.bancolombia.api.dto.request.BranchRequestDto;
import co.com.bancolombia.api.dto.request.FranchiseRequestDto;
import co.com.bancolombia.api.dto.request.ProductRequestDto;
import co.com.bancolombia.api.dto.request.UpdateStockRequestDto;
import co.com.bancolombia.api.dto.response.ApiResponse;
import co.com.bancolombia.api.dto.response.BranchProductResponseDto;
import co.com.bancolombia.api.dto.response.BranchResponseDto;
import co.com.bancolombia.api.dto.response.FranchiseResponseDto;
import co.com.bancolombia.api.dto.response.ProductResponseDto;
import co.com.bancolombia.api.mapper.ObjectMapper;
import co.com.bancolombia.exception.FranchiseException;
import co.com.bancolombia.gateways.api.BusinessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

import static co.com.bancolombia.api.constants.constants.ERROR_MISSING_NAME_QUERY_PARAM;
import static co.com.bancolombia.api.constants.constants.NAME;
import static co.com.bancolombia.api.constants.constants.PATH_VARIABLE_BRANCH_ID;
import static co.com.bancolombia.api.constants.constants.PATH_VARIABLE_FRANCHISE_ID;
import static co.com.bancolombia.api.constants.constants.PATH_VARIABLE_PRODUCT_ID;

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
        return Mono.zip(
                        Mono.just(request.pathVariable(PATH_VARIABLE_BRANCH_ID)),
                        Mono.just(request.pathVariable(PATH_VARIABLE_PRODUCT_ID)),
                        request.bodyToMono(UpdateStockRequestDto.class)
                )
                .flatMap(tuple -> {
                    Long branchId = Long.valueOf(tuple.getT1());
                    Long productId = Long.valueOf(tuple.getT2());
                    int stock = tuple.getT3().getStock();
                    return businessRepository.updateProductStock(branchId, productId, stock);
                })
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> removeProductFromBranch(ServerRequest request) {
        return Mono.zip(
                        Mono.just(request.pathVariable(PATH_VARIABLE_BRANCH_ID)),
                        Mono.just(request.pathVariable(PATH_VARIABLE_PRODUCT_ID))
                )
                .flatMap(tuple -> {
                    Long branchId = Long.valueOf(tuple.getT1());
                    Long productId = Long.valueOf(tuple.getT2());
                    return businessRepository.removeProductFromBranch(branchId, productId);
                })
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> getTopProductsByBranch(ServerRequest request) {
        return Mono.just(request.pathVariable(PATH_VARIABLE_FRANCHISE_ID))
                .map(Long::valueOf)
                .flatMapMany(businessRepository::findTopProductsByBranchForFranchise)
                .collectList()
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> updateFranchiseName(ServerRequest request) {
        return Mono.justOrEmpty(request.queryParam(NAME))
                .switchIfEmpty(Mono.error(new FranchiseException.InvalidQueryParamException(ERROR_MISSING_NAME_QUERY_PARAM)))
                .zipWith(Mono.just(request.pathVariable(PATH_VARIABLE_FRANCHISE_ID)).map(Long::valueOf))
                .flatMap(tuple -> {
                    String name = tuple.getT1();
                    Long franchiseId = tuple.getT2();
                    return businessRepository.updateFranchiseName(franchiseId, name);
                })
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> updateBranchName(ServerRequest request) {
        return Mono.justOrEmpty(request.queryParam(NAME))
                .switchIfEmpty(Mono.error(new FranchiseException.InvalidQueryParamException(ERROR_MISSING_NAME_QUERY_PARAM)))
                .zipWith(Mono.just(request.pathVariable(PATH_VARIABLE_BRANCH_ID)).map(Long::valueOf))
                .flatMap(tuple -> {
                    String newName = tuple.getT1();
                    Long branchId = tuple.getT2();
                    return businessRepository.updateBranchName(branchId, newName);
                })
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> updateProductName(ServerRequest request) {
        return Mono.justOrEmpty(request.queryParam(NAME))
                .switchIfEmpty(Mono.error(new FranchiseException.InvalidQueryParamException(ERROR_MISSING_NAME_QUERY_PARAM)))
                .zipWith(Mono.just(request.pathVariable(PATH_VARIABLE_PRODUCT_ID)).map(Long::valueOf))
                .flatMap(tuple -> {
                    String newName = tuple.getT1();
                    Long productId = tuple.getT2();
                    return businessRepository.updateProductName(productId, newName);
                })
                .then(ServerResponse.noContent().build());
    }
}
