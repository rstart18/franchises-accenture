package co.com.bancolombia.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FranchiseResponseDto {
    private long id;
    private String name;
    private String nit;
}