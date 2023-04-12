package co.com.pharmacy.usecase.product.getproductbycategory;

import co.com.pharmacy.model.product.Product;
import co.com.pharmacy.model.product.gateways.ProductGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetProductByCategoryUseCase implements Function<String, Flux<Product>> {
    private final ProductGateway productGateway;
    @Override
    public Flux<Product> apply(String productCategory) {
        return productGateway.getProductByCategory(productCategory);
    }
}
