package co.com.bancolombia.usecase.product.getallproducts;

import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.product.gateways.ProductGateway;
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
class GetAllProductsUseCaseTest {

    @Mock
    ProductGateway gateway;

    GetAllProductsUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetAllProductsUseCase(gateway);
    }

    @Test
    @DisplayName("Get all products success")
    void getAllProducts(){
        var fluxProducts = Flux.just(
                new Product(
                        "testId",
                        "test name",
                        "test description",
                        5.22, "img test",
                        "test category"),
                new Product(
                        "testId2",
                        "test name 2",
                        "test description 2",
                        5.22, "img test 2",
                        "test category 2")
        );

        Mockito.when(gateway.getAllProducts()).thenReturn(fluxProducts);

        var result = useCase.get();

        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();

        Mockito.verify(gateway, Mockito.times(1)).getAllProducts();
    }

    @Test
    @DisplayName("Get all products failed")
    void getAllProducts_Failed(){
        Mockito.when(gateway.getAllProducts())
                .thenReturn(Flux.error(new Throwable(Integer.toString(
                        HttpURLConnection.HTTP_NOT_FOUND))));

        var result = useCase.get();

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(
                                HttpURLConnection.HTTP_NOT_FOUND)))
                .verify();

        Mockito.verify(gateway, Mockito.times(1)).getAllProducts();
    }
}
