package co.com.bancolombia.usecase.product.getallproducts;

import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.product.gateways.ProductGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class GetAllProductsUseCase implements Supplier<Flux<Product>> {
    private final ProductGateway productGateway;
    @Override
    public Flux<Product> get() {
        return productGateway.getAllProducts();
    }
}
