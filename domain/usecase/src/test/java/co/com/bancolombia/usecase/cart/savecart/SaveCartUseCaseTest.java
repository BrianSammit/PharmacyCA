package co.com.bancolombia.usecase.cart.savecart;

import co.com.bancolombia.model.cart.Cart;
import co.com.bancolombia.model.cart.gateways.CartGateway;
import co.com.bancolombia.model.product.Product;
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
class SaveCartUseCaseTest {

    @Mock
    CartGateway gateway;

    SaveCartUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new SaveCartUseCase(gateway);
    }

    @Test
    @DisplayName("Save cart success")
    void saveCart() {
        Set<Product> listProducts = new HashSet<>();
        listProducts.add(
                new Product(
                        "testId",
                        "test name",
                        "test description",
                        5.22, "img test",
                        "test category")
        );

        var cart = new Cart("1", listProducts, 50.42);

        Mockito.when(gateway.saveCart(cart)).thenReturn(Mono.just(cart));

        var result = useCase.apply(cart);

        StepVerifier.create(result)
                .expectNext(cart)
                .expectComplete()
                .verify();

        Mockito.verify(gateway, Mockito.times(1)).saveCart(cart);
    }

    @Test
    @DisplayName("Save cart failed")
    void saveCart_Failed() {
        Set<Product> listProducts = new HashSet<>();
        listProducts.add(
                new Product(
                        "testId",
                        "test name",
                        "test description",
                        5.22, "img test",
                        "test category")
        );

        var cart = new Cart("1", listProducts, 50.42);

        Mockito.when(gateway.saveCart(Mockito.any(Cart.class)))
                .thenReturn(Mono.error(new Throwable(Integer.toString(
                        HttpURLConnection.HTTP_BAD_REQUEST))));

        var result = useCase.apply(cart);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(
                                HttpURLConnection.HTTP_BAD_REQUEST)))
                .verify();

        Mockito.verify(gateway, Mockito.times(1)).saveCart(cart);
    }
}