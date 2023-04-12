package co.com.pharmacy.usecase.product.deleteproduct;

import co.com.pharmacy.model.product.gateways.ProductGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class DeleteProductUseCase implements Function<String, Mono<Void>>{
    private final ProductGateway productGateway;
    @Override
    public Mono<Void> apply(String productId) {
        return productGateway.deleteProduct(productId);
    }
}
