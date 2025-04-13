package co.com.bancolombia.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Branch {
    private Long id;
    private String name;
    private Long franchiseId;
    private List<BranchProduct> products;
}