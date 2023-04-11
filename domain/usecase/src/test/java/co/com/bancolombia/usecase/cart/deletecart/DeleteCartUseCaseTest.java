package co.com.bancolombia.usecase.cart.deletecart;

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
class DeleteCartUseCaseTest {

    @Mock
    CartGateway gateway;

    DeleteCartUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new DeleteCartUseCase(gateway);
    }

    @Test
    @DisplayName("Delete cart success")
    void deleteCart() {
        Set<Product> listItems = new HashSet<>();
        listItems.add(
                new Product(
                        "testId",
                        "test name",
                        "test description",
                        5.22, "img test",
                        "test category")
        );

        var cart = new Cart("1", listItems, 968.452);

        Mockito.when(gateway.deleteCart("1")).thenReturn(Mono.empty());

        var result = useCase.apply("1");

        StepVerifier.create(result)
                .expectNext()
                .expectComplete()
                .verify();

        Mockito.verify(gateway, Mockito.times(1)).deleteCart("1");
    }

    @Test
    @DisplayName("Delete cart failed")
    void deleteCart_Failed() {
        Mockito.when(gateway.deleteCart(Mockito.any(String.class)))
                .thenReturn(Mono.error(new Throwable(Integer.toString(HttpURLConnection.HTTP_NOT_FOUND))));

        var result = useCase.apply("1");

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(HttpURLConnection.HTTP_NOT_FOUND)))
                .verify();

        Mockito.verify(gateway, Mockito.times(1)).deleteCart("1");

    }
}