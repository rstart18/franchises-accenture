package co.com.bancolombia.api.exception;

import co.com.bancolombia.exception.FranchiseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.Map;

import static java.util.Map.entry;


@ControllerAdvice(assignableTypes = FranchiseControllerAdvice.class)
public class FranchiseControllerAdvice {

    private static final Map<Class<? extends RuntimeException>, HttpStatus> STATUS_MAP = Map.ofEntries(
            entry(FranchiseException.AlreadyExistsException.class, HttpStatus.BAD_REQUEST),
            entry(FranchiseException.NotFoundException.class, HttpStatus.NOT_FOUND)
    );

    @ExceptionHandler(RuntimeException.class)
    public Mono<ResponseEntity<ErrorObjectDto>> handle(Exception ex, ServerHttpRequest request) {
        HttpStatus status = STATUS_MAP.getOrDefault(ex.getClass(), HttpStatus.INTERNAL_SERVER_ERROR);
        ErrorObjectDto error = ErrorObjectDto.builder()
                .message(ex.getMessage())
                .path(request.getPath().value())
                .status(status.value())
                .timestamp(OffsetDateTime.now().toString())
                .build();
        return Mono.just(ResponseEntity.status(status).body(error));
    }
}