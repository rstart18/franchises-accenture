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
public class Franchise {
    private Long id;
    private String name;
    private String nit;
    private List<Branch> branches;
}