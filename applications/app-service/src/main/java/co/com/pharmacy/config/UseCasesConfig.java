package co.com.pharmacy.config;

import co.com.pharmacy.model.cart.gateways.CartGateway;
import co.com.pharmacy.model.product.gateways.ProductGateway;
import co.com.pharmacy.usecase.cart.addproducttocart.AddProductToCartUseCase;
import co.com.pharmacy.usecase.cart.deletecart.DeleteCartUseCase;
import co.com.pharmacy.usecase.cart.getcartbyid.GetCartByIdUseCase;
import co.com.pharmacy.usecase.cart.removeproductfromcart.RemoveProductFromCartUseCase;
import co.com.pharmacy.usecase.cart.savecart.SaveCartUseCase;
import co.com.pharmacy.usecase.cart.updatecart.UpdateCartUseCase;
import co.com.pharmacy.usecase.product.deleteproduct.DeleteProductUseCase;
import co.com.pharmacy.usecase.product.getallproducts.GetAllProductsUseCase;
import co.com.pharmacy.usecase.product.getproductbycategory.GetProductByCategoryUseCase;
import co.com.pharmacy.usecase.product.getproductbyid.GetProductByIdUseCase;
import co.com.pharmacy.usecase.product.getproductbyname.GetProductByNameUseCase;
import co.com.pharmacy.usecase.product.getproductbyprice.GetProductByPriceUseCase;
import co.com.pharmacy.usecase.product.saveproduct.SaveProductUseCase;
import co.com.pharmacy.usecase.product.updateproduct.UpdateProductUseCase;
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

        @Bean
        public GetCartByIdUseCase getCartByIdUseCase(CartGateway gateway){
                return new GetCartByIdUseCase(gateway);
        }

        @Bean
        public SaveCartUseCase saveCartUseCase(CartGateway gateway){
                return new SaveCartUseCase(gateway);
        }

        @Bean
        public AddProductToCartUseCase addProductToCartUseCase(CartGateway gateway){
                return new AddProductToCartUseCase(gateway);
        }

        @Bean
        public DeleteCartUseCase deleteCartUseCase(CartGateway gateway){
                return new DeleteCartUseCase(gateway);
        }

        @Bean
        public UpdateCartUseCase updateCartUseCase(CartGateway gateway){
                return new UpdateCartUseCase(gateway);
        }

        @Bean
        public RemoveProductFromCartUseCase removeProductFromCartUseCase(CartGateway gateway){
                return new RemoveProductFromCartUseCase(gateway);
        }
}
