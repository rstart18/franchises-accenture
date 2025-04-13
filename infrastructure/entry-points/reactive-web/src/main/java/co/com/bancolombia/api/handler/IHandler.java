package co.com.bancolombia.api.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface IHandler {
    public Mono<ServerResponse> createBranch(ServerRequest serverRequest);

    public Mono<ServerResponse> addProductToBranch(ServerRequest request);

    public Mono<ServerResponse> createProduct(ServerRequest serverRequest);

    public Mono<ServerResponse> createFranchise(ServerRequest serverRequest);
}
