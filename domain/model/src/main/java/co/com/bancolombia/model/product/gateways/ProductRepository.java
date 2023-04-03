package co.com.bancolombia.model.product.gateways;

import co.com.bancolombia.model.product.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {

    Flux<Product> getAllProducts();

    Mono<Product> getProductById(String ProductId);

    Mono<Product> saveProduct( Product Product);

    Mono<Product> updateProduct( String ProductId, Product Product);

    Mono<Void> deleteProduct( String ProductId);
}
