package co.com.pharmacy.usecase.cart.updatecart;

import co.com.pharmacy.model.cart.Cart;
import co.com.pharmacy.model.cart.gateways.CartGateway;
import co.com.pharmacy.model.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.HttpURLConnection;
import java.util.HashSet;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class UpdateCartUseCaseTest {
    @Mock
    CartGateway gateway;

    UpdateCartUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpdateCartUseCase(gateway);
    }

    @Test
    @DisplayName("Update cart success")
    void updateCart() {

        var originalCart = new Cart("1", new HashSet<>(), 0.0);

        Set<Product> listItems = new HashSet<>();
        listItems.add(
                new Product(
                        "testId",
                        "test name",
                        "test description",
                        5.22, "img test",
                        "test category")
        );

        var updatedCart = new Cart("1", listItems, 65.45);

        Mockito.when(gateway.updateCart("1", updatedCart)).thenReturn(Mono.just(updatedCart));

        var result = useCase.apply("1", updatedCart);

        StepVerifier.create(result)
                .expectNext(updatedCart)
                .expectComplete()
                .verify();

        Mockito.verify(gateway, Mockito.times(1)).updateCart("1",updatedCart);
    }

    @Test
    @DisplayName("Update cart failed")
    void updateCart_Failed() {

        var originalCart = new Cart("1", new HashSet<>(), 0.0);

        Set<Product> listItems = new HashSet<>();
        listItems.add(
                new Product(
                        "testId",
                        "test name",
                        "test description",
                        5.22, "img test",
                        "test category")
        );

        var updatedCart = new Cart("1", listItems, 60.45);

        Mockito.when(gateway.updateCart(Mockito.any(String.class), Mockito.any(Cart.class)))
                .thenReturn(Mono.error(new Throwable(Integer.toString(
                        HttpURLConnection.HTTP_BAD_REQUEST))));

        var result = useCase.apply("1", updatedCart);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(
                                HttpURLConnection.HTTP_BAD_REQUEST)))
                .verify();

        Mockito.verify(gateway, Mockito.times(1)).updateCart("1", updatedCart);
    }
}
