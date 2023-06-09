package co.com.pharmacy.mongo;

import co.com.pharmacy.model.product.Product;
import co.com.pharmacy.model.product.gateways.ProductGateway;
import co.com.pharmacy.mongo.data.ProductData;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class MongoRepositoryAdapterProduct implements ProductGateway {

    private final MongoDBRepositoryProduct repository;
    private final ObjectMapper mapper;


    @Override
    public Flux<Product> getAllProducts() {
        return this.repository
                .findAll()
                .switchIfEmpty(Flux.empty())
                .map(productData -> mapper.map(productData, Product.class));
    }

    @Override
    public Mono<Product> getProductById(String productId) {
        return this.repository
                .findById(productId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("There is not " +
                        "Product with id: " + productId)))
                .map(productData -> mapper.map(productData, Product.class));

    }

    @Override
    public Flux<Product> getProductByCategory(String productCategory) {
        return this.repository
                .findAll()
                .switchIfEmpty(Flux.empty())
                .filter(productData -> productData.getCategory().toLowerCase().startsWith(productCategory.toLowerCase()))
                .map(productData -> mapper.map(productData, Product.class));
    }

    @Override
    public Flux<Product> getProductByName(String productName) {
        return this.repository
                .findAll()
                .switchIfEmpty(Flux.empty())
                .filter(productData -> productData.getName().toLowerCase().startsWith(productName.toLowerCase()))
                .map(productData -> mapper.map(productData, Product.class));
    }

    @Override
    public Flux<Product> getProductByPrice(Double productPrice) {
        return this.repository
                .findAll()
                .switchIfEmpty(Flux.empty())
                .filter(productData -> productData.getPrice().equals(productPrice) )
                .map(productData -> mapper.map(productData, Product.class));
    }

    @Override
    public Mono<Product> saveProduct( Product product) {
        return this.repository
                .save(mapper.map(product, ProductData.class))
                .switchIfEmpty(Mono.empty())
                .map(productData -> mapper.map(productData, Product.class));
    }

    @Override
    public Mono<Product> updateProduct(String productId, Product product) {
        return this.repository
                .findById(productId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("There is not " +
                        "product with id: " + productId)))
                .flatMap(productData -> {
                    product.setId(productData.getId());
                    return repository.save(mapper.map(product, ProductData.class));
                })
                .map(productData -> mapper.map(productData, Product.class));
    }

    @Override
    public Mono<Void> deleteProduct(String productId) {
        return this.repository
                .findById(productId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("There is not " +
                        "product with id: " + productId)))
                .flatMap(userData -> this.repository.deleteById(userData.getId()));
    }
}

