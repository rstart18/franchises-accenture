package co.com.bancolombia.api.handler;

import co.com.bancolombia.api.dto.request.ProductRequestDto;
import co.com.bancolombia.api.mapper.ProductMapper;
import co.com.bancolombia.gateways.api.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler implements IHandler {
    private final ProductMapper productMapper;
    private final FranchiseRepository franchiseRepository;
    //private final UseCase2 useCase2;

    @Override
    public Mono<ServerResponse> createProduct(ServerRequest request) {
        return request.bodyToMono(ProductRequestDto.class)
                .flatMap(productRequestDto ->
                        this.franchiseRepository.createProduct(this.productMapper.toModel(productRequestDto))
                                .map(this.productMapper::toDto)
                                .flatMap(productResponseDto ->
                                        ServerResponse.status(HttpStatus.CREATED)
                                                .bodyValue(productResponseDto)));
    }

    @Override
    public Mono<ServerResponse> createBranch(ServerRequest serverRequest) {
        return null;
    }

    @Override
    public Mono<ServerResponse> createFranchise(ServerRequest serverRequest) {
        return null;
    }


}
