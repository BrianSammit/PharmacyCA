package co.com.bancolombia.usecase.product.getproductbyprice;

import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.product.gateways.ProductGateway;
import co.com.bancolombia.usecase.product.getproductbyname.GetProductByNameUseCase;
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
class GetProductByPriceUseCaseTest {

    @Mock
    ProductGateway gateway;

    GetProductByPriceUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetProductByPriceUseCase(gateway);
    }

    @Test
    @DisplayName("Get product by price success")
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

        Mockito.when(gateway.getProductByPrice(5.22)).thenReturn(Flux.concat(fluxProducts.elementAt(0)));

        var result = useCase.apply(5.22);

        StepVerifier.create(result)
                .expectNext(fluxProducts.elementAt(0).block())
                .expectComplete()
                .verify();

        Mockito.verify(gateway, Mockito.times(1)).getProductByPrice(5.22);
    }

    @Test
    @DisplayName("Get product by price failed")
    void getProductByName_Failed() {
        Mockito.when(gateway.getProductByPrice(Mockito.any(Double.class)))
                .thenReturn(Flux.error(new Throwable(Integer.toString(
                        HttpURLConnection.HTTP_NOT_FOUND))));

        var result = useCase.apply(5.22);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(
                                HttpURLConnection.HTTP_NOT_FOUND)))
                .verify();

        Mockito.verify(gateway, Mockito.times(1)).getProductByPrice(5.22);

    }
}
