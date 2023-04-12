package co.com.pharmacy.usecase.cart.savecart;

import co.com.pharmacy.model.cart.Cart;
import co.com.pharmacy.model.cart.gateways.CartGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class SaveCartUseCase implements Function<Cart, Mono<Cart>> {
    private final CartGateway cartGateway;

    @Override
    public Mono<Cart> apply(Cart cart) {
        return cartGateway.saveCart(cart);
    }
}
