package co.com.bancolombia.config;

import co.com.bancolombia.model.product.gateways.ProductGateway;
import co.com.bancolombia.usecase.product.getallproducts.GetAllProductsUseCase;
import co.com.bancolombia.usecase.product.saveproduct.SaveProductUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "co.com.bancolombia.usecase")
//        includeFilters = {
//                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
//        },
//        useDefaultFilters = false)
public class UseCasesConfig {
        @Bean
        public GetAllProductsUseCase getAllProductsUseCase(ProductGateway gateway){
                return new GetAllProductsUseCase(gateway);
        }

//        @Bean
//        public GetProductByIdUseCase getProductByIDUseCase(ProductRepository gateway){
//                return new GetProductByIdUseCase(gateway);
//        }

        @Bean
        public SaveProductUseCase saveProductUseCaseUseCase(ProductGateway gateway){
                return new SaveProductUseCase(gateway);
        }

//        @Bean
//        public UpdateProductUseCase updateProductUseCase(ProductRepository gateway){
//                return new UpdateProductUseCase(gateway);
//        }

//        @Bean
//        public DeleteProductUseCase deleteProductUseCase(ProductRepository gateway){
//                return new DeleteProductUseCase(gateway);
//        }
}
