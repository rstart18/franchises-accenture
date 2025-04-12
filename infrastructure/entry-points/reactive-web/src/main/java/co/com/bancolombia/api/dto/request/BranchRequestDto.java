package co.com.bancolombia.api.dto.request;


import lombok.Data;

@Data
public class BranchRequestDto {
    private String name;
    private long franchiseId;
}
