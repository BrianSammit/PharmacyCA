package co.com.pharmacy.usecase.cart.deletecart;

import co.com.pharmacy.model.cart.gateways.CartGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class DeleteCartUseCase implements Function<String, Mono<Void>> {
    private final CartGateway cartGateway;

    @Override
    public Mono<Void> apply(String cartId) {
        return cartGateway.deleteCart(cartId);
    }
}
