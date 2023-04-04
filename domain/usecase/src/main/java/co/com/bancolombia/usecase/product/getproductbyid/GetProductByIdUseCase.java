package co.com.bancolombia.usecase.product.getproductbyid;

import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.product.gateways.ProductGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetProductByIdUseCase implements Function<String, Mono<Product>> {

    private final ProductGateway productGateway;
    @Override
    public Mono<Product> apply(String productId) {
        return productGateway.getProductById(productId);
    }
}
