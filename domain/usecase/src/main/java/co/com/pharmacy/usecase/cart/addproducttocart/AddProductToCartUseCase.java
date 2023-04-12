package co.com.pharmacy.usecase.cart.addproducttocart;

import co.com.pharmacy.model.cart.Cart;
import co.com.pharmacy.model.cart.gateways.CartGateway;
import co.com.pharmacy.model.product.Product;
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
