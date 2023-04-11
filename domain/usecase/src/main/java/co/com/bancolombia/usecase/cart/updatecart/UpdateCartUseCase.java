package co.com.bancolombia.usecase.cart.updatecart;

import co.com.bancolombia.model.cart.Cart;
import co.com.bancolombia.model.cart.gateways.CartGateway;
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
