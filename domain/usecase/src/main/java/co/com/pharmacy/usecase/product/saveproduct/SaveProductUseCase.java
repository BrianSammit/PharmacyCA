package co.com.pharmacy.usecase.product.saveproduct;

import co.com.pharmacy.model.product.Product;
import co.com.pharmacy.model.product.gateways.ProductGateway;
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
