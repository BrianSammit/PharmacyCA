package co.com.pharmacy.model.product.gateways;

import co.com.pharmacy.model.product.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductGateway {

    Flux<Product> getAllProducts();

    Mono<Product> getProductById(String productId);

    Flux<Product> getProductByCategory(String productCategory);

    Flux<Product> getProductByName(String productName);

    Flux<Product> getProductByPrice(Double productPrice);

    Mono<Product> saveProduct( Product product);

    Mono<Product> updateProduct( String productId, Product product);

    Mono<Void> deleteProduct( String productId);
}
