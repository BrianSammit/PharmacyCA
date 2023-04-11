package co.com.bancolombia.usecase.cart.addproducttocart;

import co.com.bancolombia.model.cart.Cart;
import co.com.bancolombia.model.cart.gateways.CartGateway;
import co.com.bancolombia.model.product.Product;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class AddProductToCartUseCase implements BiFunction<String, Product, Mono<Cart>> {
    private final CartGateway cartGateway;

    @Override
    public Mono<Cart> apply(String cartId, Product product) {
        return cartGateway.addProductToCart(cartId, product);
    }
}
