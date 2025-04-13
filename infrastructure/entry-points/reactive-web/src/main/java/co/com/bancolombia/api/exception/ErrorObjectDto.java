package co.com.bancolombia.api.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorObjectDto {
    private String message;
    private String path;
    private int status;
    private String timestamp;
}
