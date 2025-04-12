package co.com.bancolombia.config;

import co.com.bancolombia.gateways.spi.StorageRepository;
import co.com.bancolombia.usecase.business.BusinessUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "co.com.bancolombia.usecase",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        },
        useDefaultFilters = false)
public class UseCasesConfig {
        @Bean
        public BusinessUseCase businessUseCase(StorageRepository storageRepository) {
                return new BusinessUseCase(storageRepository);
        }
}
