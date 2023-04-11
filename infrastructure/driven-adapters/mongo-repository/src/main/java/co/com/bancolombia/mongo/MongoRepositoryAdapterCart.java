package co.com.bancolombia.mongo;

import co.com.bancolombia.model.cart.Cart;
import co.com.bancolombia.model.cart.gateways.CartGateway;
import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.mongo.data.CartData;
import co.com.bancolombia.mongo.data.ProductData;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class MongoRepositoryAdapterCart implements CartGateway {
    private final MongoDBRepositoryCart repository;

    private final ObjectMapper mapper;
    @Override
    public Mono<Cart> getCartById(String cartId) {
        return this.repository
                .findById(cartId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("There is not " +
                        "cart with id: " + cartId)))
                .map(cartData -> mapper.map(cartData, Cart.class));
    }

    @Override
    public Mono<Cart> saveCart(Cart cart) {
        return this.repository
                .save(mapper.map(cart, CartData.class))
                .switchIfEmpty(Mono.empty())
                .map(cartData -> mapper.map(cartData, Cart.class));
    }

    @Override
    public Mono<Cart> addProductToCart(String cartId, Product product) {
        return this.repository
                .findById(cartId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("There is not " +
                        "cart with id: " + cartId)))
                .flatMap(cartData -> {
                    var listOfItems = cartData.getProducts();
                    listOfItems.add(mapper.map(product, ProductData.class));
                    cartData.setProducts(listOfItems);
                    cartData.setTotalPrice(
                            listOfItems.stream().mapToDouble(ProductData::getPrice).sum());
                    return this.repository.save(cartData);
                })
                .map(cartData -> mapper.map(cartData, Cart.class));
    }

    @Override
    public Mono<Cart> removeProductFromCart(String cartId, Product product) {
        return this.repository
                .findById(cartId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("There is not " +
                        "cart with id: " + cartId)))
                .flatMap(cartData -> {
                    var listOfItems = cartData.getProducts();
                    listOfItems.stream().forEach(productData -> {
                        if (productData.getId().equals(product.getId())) listOfItems.remove(productData);
                    });
                    cartData.setProducts(listOfItems);
                    cartData.setTotalPrice(
                            listOfItems.stream().mapToDouble(ProductData::getPrice).sum());
                    return this.repository.save(cartData);
                })
                .map(cartData -> mapper.map(cartData, Cart.class));
    }

    @Override
    public Mono<Cart> updateCart(String cartId, Cart cart) {
        return this.repository
                .findById(cartId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("There is not " +
                        "cart with id: " + cartId)))
                .flatMap(cartData -> {
                    cart.setId(cartData.getId());
                    cart.getProducts().stream().forEach(product -> cart.setTotalPrice(cart.getTotalPrice()+product.getPrice()));
                    return repository.save(mapper.map(cart, CartData.class));
                })
                .map(cartData -> mapper.map(cartData, Cart.class));
    }

    @Override
    public Mono<Void> deleteCart(String cartId) {
        return this.repository
                .findById(cartId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("There is not " +
                        "cart with id: " + cartId)))
                .flatMap(cartData -> this.repository.deleteById(cartData.getId()));
    }
}
