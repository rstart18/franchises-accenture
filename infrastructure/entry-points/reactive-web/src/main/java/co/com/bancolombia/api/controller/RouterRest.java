package co.com.bancolombia.api.controller;

import co.com.bancolombia.api.handler.IHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static co.com.bancolombia.api.constants.constants.FRANCHISE_ROUTER_FUNCTION;
import static co.com.bancolombia.api.constants.constants.PATH_BRANCH;
import static co.com.bancolombia.api.constants.constants.PATH_BRANCH_PRODUCT;
import static co.com.bancolombia.api.constants.constants.PATH_DELETE_PRODUCT_FROM_BRANCH;
import static co.com.bancolombia.api.constants.constants.PATH_FRANCHISE;
import static co.com.bancolombia.api.constants.constants.PATH_PRODUCT;
import static co.com.bancolombia.api.constants.constants.PATH_TOP_PRODUCTS_BY_BRANCH;
import static co.com.bancolombia.api.constants.constants.PATH_UPDATE_STOCK;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;

@Configuration
public class RouterRest {
    @Bean(FRANCHISE_ROUTER_FUNCTION)
    public RouterFunction<ServerResponse> routerFunction(IHandler handler) {
        return RouterFunctions
                .route(GET(PATH_TOP_PRODUCTS_BY_BRANCH), handler::getTopProductsByBranch)
                .andRoute(POST(PATH_PRODUCT), handler::createProduct)
                .andRoute(POST(PATH_BRANCH_PRODUCT), handler::addProductToBranch)
                .andRoute(POST(PATH_BRANCH), handler::createBranch)
                .andRoute(POST(PATH_FRANCHISE), handler::createFranchise)
                .andRoute(PUT(PATH_UPDATE_STOCK), handler::updateProductStock)
                .andRoute(DELETE(PATH_DELETE_PRODUCT_FROM_BRANCH), handler::removeProductFromBranch);
    }
}
