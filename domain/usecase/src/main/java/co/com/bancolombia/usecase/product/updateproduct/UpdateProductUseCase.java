package co.com.bancolombia.usecase.product.updateproduct;

import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.product.gateways.ProductGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class UpdateProductUseCase  implements BiFunction<String, Product, Mono<Product>> {

    private final ProductGateway productGateway;
    @Override
    public Mono<Product> apply(String productId, Product product) {
        return productGateway.updateProduct(productId, product);
    }
}
