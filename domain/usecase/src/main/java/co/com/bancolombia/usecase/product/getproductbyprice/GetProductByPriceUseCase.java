package co.com.bancolombia.usecase.product.getproductbyprice;

import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.product.gateways.ProductGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetProductByPriceUseCase implements Function<Double, Flux<Product>> {
    private final ProductGateway productGateway;
    @Override
    public Flux<Product> apply(Double productPrice) {
        return productGateway.getProductByPrice(productPrice);
    }
}
