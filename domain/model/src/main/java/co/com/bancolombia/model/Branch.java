package co.com.bancolombia.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
//@NoArgsConstructor
@Builder(toBuilder = true)
public class Branch {
    private Long id;
    private String name;
    private Long franchiseId;

    private List<BranchProduct> products;
}