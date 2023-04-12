package co.com.pharmacy.usecase.product.getproductbycategory;

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
class GetProductByCategoryUseCaseTest {


    @Mock
    ProductGateway gateway;

    GetProductByCategoryUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetProductByCategoryUseCase(gateway);
    }

    @Test
    @DisplayName("Get product by category success")
    void getProductByCategory() {
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

        Mockito.when(gateway.getProductByCategory("test category")).thenReturn(fluxProducts);

        var result = useCase.apply("test category");

        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();

        Mockito.verify(gateway, Mockito.times(1)).getProductByCategory("test category");
    }

    @Test
    @DisplayName("Get product by category failed")
    void getProductByCategory_Failed() {
        Mockito.when(gateway.getProductByCategory(Mockito.any(String.class)))
                .thenReturn(Flux.error(new Throwable(Integer.toString(
                        HttpURLConnection.HTTP_NOT_FOUND))));

        var result = useCase.apply("test category");

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(
                                HttpURLConnection.HTTP_NOT_FOUND)))
                .verify();

        Mockito.verify(gateway, Mockito.times(1))
                .getProductByCategory("test category");

    }
}
