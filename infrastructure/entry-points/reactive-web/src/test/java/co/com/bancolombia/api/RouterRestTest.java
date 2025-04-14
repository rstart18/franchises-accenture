package co.com.bancolombia.api;

import co.com.bancolombia.api.controller.RouterRest;
import co.com.bancolombia.api.dto.request.FranchiseRequestDto;
import co.com.bancolombia.api.dto.request.ProductRequestDto;
import co.com.bancolombia.api.dto.response.ApiResponse;
import co.com.bancolombia.api.dto.response.FranchiseResponseDto;
import co.com.bancolombia.api.dto.response.ProductResponseDto;
import co.com.bancolombia.api.handler.Handler;
import co.com.bancolombia.api.handler.IHandler;
import co.com.bancolombia.api.mapper.ObjectMapper;
import co.com.bancolombia.gateways.api.BusinessRepository;
import co.com.bancolombia.model.Franchise;
import co.com.bancolombia.model.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = RouterRest.class)
@ContextConfiguration(classes = RouterRestTest.TestConfig.class)
@Import({RouterRest.class, Handler.class})
class RouterRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BusinessRepository businessRepository;

    @Test
    void testCreateProductPOST() {
        ProductRequestDto request = new ProductRequestDto();
        request.setName("Producto 1");

        Product model = Product.builder()
                .name("Producto 1")
                .build();
        ProductResponseDto expectedResponse = ProductResponseDto.builder()
                .name("Producto 1").
                build();

        when(objectMapper.toModel(any(ProductRequestDto.class))).thenReturn(model);
        when(businessRepository.createProduct(any(Product.class))).thenReturn(Mono.just(model));
        when(objectMapper.toDto(any(Product.class))).thenReturn(expectedResponse);

        webTestClient.post()
                .uri("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ApiResponse.class)
                .value(response -> {
                    Assertions.assertThat(response).isNotNull();
                    ApiResponse<ProductResponseDto> api = response;
                    Assertions.assertThat(api.getData()).isNotEmpty();
                });

        verify(objectMapper).toModel(any(ProductRequestDto.class));
        verify(businessRepository).createProduct(any(Product.class));
        verify(objectMapper).toDto(any(Product.class));
    }

    @Test
    void testCreateFranchisePOST() {
        FranchiseRequestDto request = new FranchiseRequestDto();
        request.setName("Franquicia 1");

        Franchise model = Franchise.builder()
                .name("Franquicia 1")
                .build();

        FranchiseResponseDto expectedResponse = FranchiseResponseDto.builder()
                .name("Franquicia 1")
                .build();

        when(objectMapper.toModel(any(FranchiseRequestDto.class))).thenReturn(model);
        when(businessRepository.createFranchise(any(Franchise.class))).thenReturn(Mono.just(model));
        when(objectMapper.toDto(any(Franchise.class))).thenReturn(expectedResponse);

        webTestClient.post()
                .uri("/api/v1/franchise")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ApiResponse.class)
                .value(response -> {
                    Assertions.assertThat(response).isNotNull();
                    ApiResponse<FranchiseResponseDto> api = response;
                    Assertions.assertThat(api.getData()).isNotEmpty();
                });

        // Verify interactions
        verify(objectMapper).toModel(any(FranchiseRequestDto.class));
        verify(businessRepository).createFranchise(any(Franchise.class));
        verify(objectMapper).toDto(any(Franchise.class));
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        public ObjectMapper objectMapper() {
            return mock(ObjectMapper.class);
        }

        @Bean
        public BusinessRepository businessRepository() {
            return mock(BusinessRepository.class);
        }

        @Bean
        @Primary
        public IHandler handler(ObjectMapper objectMapper, BusinessRepository businessRepository) {
            return new Handler(objectMapper, businessRepository);
        }
    }


//    @Test
//    void testListenGETUseCase() {
//        webTestClient.get()
//                .uri("/api/usecase/path")
//                .accept(MediaType.APPLICATION_JSON)
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(String.class)
//                .value(userResponse -> {
//                            Assertions.assertThat(userResponse).isEmpty();
//                        }
//                );
//    }

//    @Test
//    void testListenGETOtherUseCase() {
//        webTestClient.get()
//                .uri("/api/otherusercase/path")
//                .accept(MediaType.APPLICATION_JSON)
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(String.class)
//                .value(userResponse -> {
//                            Assertions.assertThat(userResponse).isEmpty();
//                        }
//                );
//    }
}
