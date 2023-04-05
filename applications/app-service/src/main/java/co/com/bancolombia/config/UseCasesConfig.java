package co.com.bancolombia.config;

import co.com.bancolombia.model.product.gateways.ProductGateway;
import co.com.bancolombia.usecase.product.deleteproduct.DeleteProductUseCase;
import co.com.bancolombia.usecase.product.getallproducts.GetAllProductsUseCase;
import co.com.bancolombia.usecase.product.getproductbycategory.GetProductByCategoryUseCase;
import co.com.bancolombia.usecase.product.getproductbyid.GetProductByIdUseCase;
import co.com.bancolombia.usecase.product.getproductbyname.GetProductByNameUseCase;
import co.com.bancolombia.usecase.product.getproductbyprice.GetProductByPriceUseCase;
import co.com.bancolombia.usecase.product.saveproduct.SaveProductUseCase;
import co.com.bancolombia.usecase.product.updateproduct.UpdateProductUseCase;
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

        @Bean
        public GetProductByIdUseCase getProductByIDUseCase(ProductGateway gateway){
                return new GetProductByIdUseCase(gateway);
        }

        @Bean
        public GetProductByCategoryUseCase getProductByCategoryUseCase(ProductGateway gateway){
                return new GetProductByCategoryUseCase(gateway);
        }

        @Bean
        public GetProductByNameUseCase getProductByNameUseCase(ProductGateway gateway){
                return new GetProductByNameUseCase(gateway);
        }

        @Bean
        public GetProductByPriceUseCase getProductByPriceUseCase(ProductGateway gateway){
                return new GetProductByPriceUseCase(gateway);
        }

        @Bean
        public SaveProductUseCase saveProductUseCaseUseCase(ProductGateway gateway){
                return new SaveProductUseCase(gateway);
        }

        @Bean
        public UpdateProductUseCase updateProductUseCase(ProductGateway gateway){
                return new UpdateProductUseCase(gateway);
        }

        @Bean
        public DeleteProductUseCase deleteProductUseCase(ProductGateway gateway){
                return new DeleteProductUseCase(gateway);
        }
}
