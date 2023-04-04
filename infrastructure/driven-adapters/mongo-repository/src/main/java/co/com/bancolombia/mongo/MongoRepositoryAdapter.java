package co.com.bancolombia.mongo;

import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.product.gateways.ProductGateway;
import co.com.bancolombia.mongo.data.ProductData;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class MongoRepositoryAdapter implements ProductGateway {

    private final MongoDBRepository repository;
    private final ObjectMapper mapper;


    @Override
    public Flux<Product> getAllProducts() {
        return null;
    }

    @Override
    public Mono<Product> getProductById(String ProductId) {
        return null;
    }

    @Override
    public Mono<Product> saveProduct( Product product) {
        return this.repository
                .save(mapper.map(product, ProductData.class))
                .switchIfEmpty(Mono.empty())
                .map(productData -> mapper.map(productData, Product.class));
    }

    @Override
    public Mono<Product> updateProduct(String ProductId, Product Product) {
        return null;
    }

    @Override
    public Mono<Void> deleteProduct(String ProductId) {
        return null;
    }
}
