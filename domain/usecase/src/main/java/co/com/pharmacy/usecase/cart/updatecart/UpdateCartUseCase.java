package co.com.pharmacy.usecase.cart.updatecart;

import co.com.pharmacy.model.cart.Cart;
import co.com.pharmacy.model.cart.gateways.CartGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class UpdateCartUseCase implements BiFunction<String, Cart, Mono<Cart>> {
    private final CartGateway cartGateway;

    @Override
    public Mono<Cart> apply(String cartId, Cart cart) {
        return cartGateway.updateCart(cartId, cart);
    }
}
