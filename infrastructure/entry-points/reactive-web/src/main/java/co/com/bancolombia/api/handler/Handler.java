package co.com.bancolombia.api.handler;

import co.com.bancolombia.api.dto.request.BranchProductRequestDto;
import co.com.bancolombia.api.dto.request.BranchRequestDto;
import co.com.bancolombia.api.dto.request.FranchiseRequestDto;
import co.com.bancolombia.api.dto.request.ProductRequestDto;
import co.com.bancolombia.api.dto.response.ApiResponse;
import co.com.bancolombia.api.dto.response.BranchProductResponseDto;
import co.com.bancolombia.api.dto.response.BranchResponseDto;
import co.com.bancolombia.api.dto.response.FranchiseResponseDto;
import co.com.bancolombia.api.dto.response.ProductResponseDto;
import co.com.bancolombia.api.mapper.ObjectMapper;
import co.com.bancolombia.gateways.api.FranchiseRepository;
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
    private final FranchiseRepository franchiseRepository;

    public Mono<ServerResponse> createFranchise(ServerRequest request) {
        return request.bodyToMono(FranchiseRequestDto.class)
                .map(objectMapper::toModel)
                .flatMap(franchiseRepository::createFranchise)
                .map(objectMapper::toDto)
                .map(dto -> ApiResponse.<FranchiseResponseDto>builder()
                        .data(List.of(dto))
                        .build())
                .flatMap(response ->
                        ServerResponse.status(HttpStatus.CREATED)
                                .bodyValue(response)
                );
    }


    @Override
    public Mono<ServerResponse> createBranch(ServerRequest request) {
        return request.bodyToMono(BranchRequestDto.class)
                .map(objectMapper::toModel)
                .flatMap(franchiseRepository::createBranch)
                .map(objectMapper::toDto)
                .map(dto -> ApiResponse.<BranchResponseDto>builder()
                        .data(List.of(dto))
                        .build())
                .flatMap(response ->
                        ServerResponse.status(HttpStatus.CREATED)
                                .bodyValue(response));
    }


    public Mono<ServerResponse> addProductToBranch(ServerRequest request) {
        return request.bodyToMono(BranchProductRequestDto.class)
                .flatMap(dto ->
                        franchiseRepository.addProductToBranch(dto.getProductName(), dto.getBranchId(), dto.getStock())
                                .map(objectMapper::toDto)
                                .map(resp -> ApiResponse.<BranchProductResponseDto>builder()
                                        .data(List.of(resp))
                                        .build())
                                .flatMap(apiResp -> ServerResponse.status(HttpStatus.CREATED).bodyValue(apiResp))
                );
    }


    @Override
    public Mono<ServerResponse> createProduct(ServerRequest request) {
        return request.bodyToMono(ProductRequestDto.class)
                .flatMap(productRequestDto ->
                        this.franchiseRepository.createProduct(this.objectMapper.toModel(productRequestDto))
                                .map(this.objectMapper::toDto)
                                .flatMap(productResponseDto ->
                                        ServerResponse.status(HttpStatus.CREATED)
                                                .bodyValue(ApiResponse.<ProductResponseDto>builder()
                                                        .data(List.of(productResponseDto))
                                                        .build())));
    }
}
