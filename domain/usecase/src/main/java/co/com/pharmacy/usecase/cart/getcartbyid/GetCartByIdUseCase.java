package co.com.pharmacy.usecase.cart.getcartbyid;

import co.com.pharmacy.model.cart.Cart;
import co.com.pharmacy.model.cart.gateways.CartGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetCartByIdUseCase implements Function<String, Mono<Cart>> {
    private final CartGateway cartGateway;

    @Override
    public Mono<Cart> apply(String cartId) {
        return cartGateway.getCartById(cartId);
    }
}
