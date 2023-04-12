package co.com.pharmacy.usecase.product.getallproducts;

import co.com.pharmacy.model.product.Product;
import co.com.pharmacy.model.product.gateways.ProductGateway;
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
