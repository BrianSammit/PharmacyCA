package co.com.pharmacy.usecase.product.getproductbyid;

import co.com.pharmacy.model.product.Product;
import co.com.pharmacy.model.product.gateways.ProductGateway;
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
class GetProductByIdUseCaseTest {

    @Mock
    ProductGateway gateway;

    GetProductByIdUseCase useCase;


    @BeforeEach
    void setUp() {
        useCase = new GetProductByIdUseCase(gateway);
    }

    @Test
    @DisplayName("Get product by id success")
    void getProductById() {
        var product = new Product(
                "testId",
                "test name",
                "test description",
                5.22, "img test",
                "test category");

        Mockito.when(gateway.getProductById("testId")).thenReturn(Mono.just(product));

        var result = useCase.apply("testId");

        StepVerifier.create(result)
                .expectNext(product)
                .expectComplete()
                .verify();

        Mockito.verify(gateway, Mockito.times(1)).getProductById("testId");
    }

    @Test
    @DisplayName("Get product by id failed")
    void getProductById_Failed() {
        Mockito.when(gateway.getProductById(Mockito.any(String.class)))
                .thenReturn(Mono.error(new Throwable(Integer.toString(
                        HttpURLConnection.HTTP_NOT_FOUND))));

        var result = useCase.apply("testId");

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(
                                HttpURLConnection.HTTP_NOT_FOUND)))
                .verify();

        Mockito.verify(gateway, Mockito.times(1))
                .getProductById("testId");
    }
}