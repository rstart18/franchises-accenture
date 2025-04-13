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
import static co.com.bancolombia.api.constants.constants.PATH_FRANCHISE;
import static co.com.bancolombia.api.constants.constants.PATH_PRODUCT;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class RouterRest {
    @Bean(FRANCHISE_ROUTER_FUNCTION)
    public RouterFunction<ServerResponse> routerFunction(IHandler handler) {
        return RouterFunctions
                .route(POST(PATH_PRODUCT), handler::createProduct)
                .andRoute(POST(PATH_BRANCH_PRODUCT), handler::addProductToBranch)
                .andRoute(POST(PATH_BRANCH), handler::createBranch)
                .andRoute(POST(PATH_FRANCHISE), handler::createFranchise);
//                .andRoute(GET(PATH_GET_BY_ID), handler::getUserById)
//                .andRoute(GET(PATH_GET_ALL_USERS), handler::getAllUsers)
//                .andRoute(GET(PATH_USER_GET_BY_NAME), handler::findUsersByName);
    }
}
