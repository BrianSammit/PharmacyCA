package co.com.pharmacy.usecase.product.getproductbyname;

import co.com.pharmacy.model.product.Product;
import co.com.pharmacy.model.product.gateways.ProductGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.net.HttpURLConnection;

@ExtendWith(MockitoExtension.class)
class GetProductByNameUseCaseTest {

    @Mock
    ProductGateway gateway;

    GetProductByNameUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetProductByNameUseCase(gateway);
    }

    @Test
    @DisplayName("Get product by name success")
    void getProductByName() {
        var fluxProducts = Flux.just(
                new Product(
                        "testId",
                        "test name",
                        "test description",
                        5.22,
                        "img test",
                        "test category"),
                new Product(
                        "testId2",
                        "test name 2",
                        "test description 2",
                        5.22,
                        "img test 2",
                        "test category 2")
        );

        Mockito.when(gateway.getProductByName("test name")).thenReturn(Flux.concat(fluxProducts.elementAt(0)));

        var result = useCase.apply("test name");

        StepVerifier.create(result)
                .expectNext(fluxProducts.elementAt(0).block())
                .expectComplete()
                .verify();

        Mockito.verify(gateway, Mockito.times(1)).getProductByName("test name");
    }

    @Test
    @DisplayName("Get product by name failed")
    void getProductByName_Failed() {
        Mockito.when(gateway.getProductByName(Mockito.any(String.class)))
                .thenReturn(Flux.error(new Throwable(Integer.toString(
                        HttpURLConnection.HTTP_NOT_FOUND))));

        var result = useCase.apply("test name");

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(
                                HttpURLConnection.HTTP_NOT_FOUND)))
                .verify();

        Mockito.verify(gateway, Mockito.times(1)).getProductByName("test name");

    }
}