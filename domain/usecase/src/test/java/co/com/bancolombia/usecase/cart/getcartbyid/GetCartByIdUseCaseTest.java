package co.com.bancolombia.usecase.cart.getcartbyid;

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
class GetCartByIdUseCaseTest {

    @Mock
    CartGateway gateway;

    GetCartByIdUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetCartByIdUseCase(gateway);
    }

    @Test
    @DisplayName("Get cart by id success")
    void getCartById() {

        Set<Product> listProducts = new HashSet<>();
        listProducts.add(
                new Product(
                        "testId",
                        "test name",
                        "test description",
                        5.22, "img test",
                        "test category")
        );

        var cart = new Cart("1", listProducts, 968.452);

        Mockito.when(gateway.getCartById("1")).thenReturn(Mono.just(cart));

        var result = useCase.apply("1");

        StepVerifier.create(result)
                .expectNext(cart)
                .expectComplete()
                .verify();

        Mockito.verify(gateway, Mockito.times(1)).getCartById("1");
    }

    @Test
    @DisplayName("Get cart by id failed")
    void getCartById_Failed() {
        Mockito.when(gateway.getCartById(Mockito.any(String.class)))
                .thenReturn(Mono.error(new Throwable(Integer.toString(
                        HttpURLConnection.HTTP_NOT_FOUND))));

        var result = useCase.apply("1");

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(
                                HttpURLConnection.HTTP_NOT_FOUND)))
                .verify();

        Mockito.verify(gateway, Mockito.times(1))
                .getCartById("1");
    }
}