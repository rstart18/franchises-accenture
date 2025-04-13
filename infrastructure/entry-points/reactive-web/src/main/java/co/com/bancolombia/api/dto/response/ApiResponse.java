package co.com.bancolombia.api.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ApiResponse<T> {
    private List<T> data;
}
