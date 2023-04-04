package co.com.bancolombia.usecase.saveproduct;

import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.product.gateways.ProductGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class SaveProductUseCase implements Function<Product, Mono<Product>> {
    private final ProductGateway gateway;

    @Override
    public Mono<Product> apply(Product product) {
        return gateway.saveProduct(product);
    }
}
