package co.com.bancolombia.api.exception;

import co.com.bancolombia.api.dto.response.ApiResponse;
import co.com.bancolombia.exception.FranchiseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;


@Component
@Order(-2)
public class GlobalErrorHandler implements ErrorWebExceptionHandler {

    private static final Map<Class<? extends Throwable>, HttpStatus> STATUS_MAP = Map.of(
            FranchiseException.AlreadyExistsException.class, HttpStatus.BAD_REQUEST,
            FranchiseException.NotFoundException.class, HttpStatus.NOT_FOUND,
            IllegalArgumentException.class, HttpStatus.CONFLICT
    );

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        HttpStatus status = resolveStatus(ex);
        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        ErrorObjectDto errorDto = ErrorObjectDto.builder()
                .message(ex.getMessage())
                .status(status.value())
                .timestamp(OffsetDateTime.now().toString())
                .path(exchange.getRequest().getPath().value())
                .build();

        ApiResponse<ErrorObjectDto> errorResponse = ApiResponse.<ErrorObjectDto>builder()
                .data(List.of(errorDto))
                .build();

        byte[] bytes;
        try {
            bytes = new ObjectMapper().writeValueAsBytes(errorResponse);
        } catch (JsonProcessingException e) {
            bytes = ("{\"message\":\"Error serializing error response\"}").getBytes(StandardCharsets.UTF_8);
        }

        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }

    private HttpStatus resolveStatus(Throwable ex) {
        return STATUS_MAP.entrySet().stream()
                .filter(entry -> entry.getKey().isAssignableFrom(ex.getClass()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
