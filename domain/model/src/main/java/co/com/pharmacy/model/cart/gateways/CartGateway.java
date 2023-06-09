package co.com.pharmacy.model.cart.gateways;

import co.com.pharmacy.model.cart.Cart;
import co.com.pharmacy.model.product.Product;
import reactor.core.publisher.Mono;

public interface CartGateway {
    Mono<Cart> getCartById(String cartId);

    Mono<Cart> saveCart(Cart cart);

    Mono<Cart> addProductToCart(String cartId, Product product);

    Mono<Cart> removeProductFromCart(String cartId, Product product);
    Mono<Cart> updateCart(String cartId, Cart cart);

    Mono<Void> deleteCart(String cartId);
}
