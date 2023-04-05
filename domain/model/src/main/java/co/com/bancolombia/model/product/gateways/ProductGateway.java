package co.com.bancolombia.model.product.gateways;

import co.com.bancolombia.model.product.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductGateway {

    Flux<Product> getAllProducts();

    Mono<Product> getProductById(String productId);

    Flux<Product> getProductByCategory(String productCategory);

    Mono<Product> saveProduct( Product product);

    Mono<Product> updateProduct( String productId, Product product);

    Mono<Void> deleteProduct( String productId);
}
