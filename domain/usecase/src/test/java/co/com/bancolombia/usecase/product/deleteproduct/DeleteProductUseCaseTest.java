package co.com.bancolombia.usecase.product.deleteproduct;

import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.product.gateways.ProductGateway;
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

@ExtendWith(MockitoExtension.class)
class DeleteProductUseCaseTest {

    @Mock
    ProductGateway gateway;

    DeleteProductUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new DeleteProductUseCase(gateway);
    }

    @Test
    @DisplayName("Delete Product Success")
    void deleteProductById() {
        var product = new Product(
                "testId",
                "test name",
                "test description",
                5.22, "img test",
                "test category");

        Mockito.when(gateway.deleteProduct("testId")).thenReturn(Mono.empty());

        var result = useCase.apply("testId");

        StepVerifier.create(result)
                .expectNext()
                .expectComplete()
                .verify();

        Mockito.verify(gateway, Mockito.times(1)).deleteProduct("testId");
    }

    @Test
    @DisplayName("Delete Product Failed")
    void deleteProductById_Failed() {
        Mockito.when(gateway.deleteProduct(Mockito.any(String.class)))
                .thenReturn(Mono.error(new Throwable(Integer.toString(HttpURLConnection.HTTP_NOT_FOUND))));

        var result = useCase.apply("1");

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(HttpURLConnection.HTTP_NOT_FOUND)))
                .verify();

        Mockito.verify(gateway, Mockito.times(1)).deleteProduct("1");
    }
}
