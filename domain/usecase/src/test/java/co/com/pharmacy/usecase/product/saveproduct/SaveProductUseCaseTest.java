package co.com.pharmacy.usecase.product.saveproduct;


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
class SaveProductUseCaseTest {

    @Mock
    ProductGateway gateway;

    SaveProductUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new SaveProductUseCase(gateway);
    }

    @Test
    @DisplayName("Save product success")
    void saveProduct() {
        var product = new Product(
                "testId",
                "test name",
                "test description",
                5.22, "img test",
                "test category");

        Mockito.when(gateway.saveProduct(product)).thenReturn(Mono.just(product));

        var result = useCase.apply(product);

        StepVerifier.create(result)
                .expectNext(product)
                .expectComplete()
                .verify();

        Mockito.verify(gateway, Mockito.times(1)).saveProduct(product);
    }

    @Test
    @DisplayName("Save product failed")
    void saveProduct_Failed() {

        var product = new Product(
                "testId",
                "test name",
                "test description",
                5.22, "img test",
                "test category");

        Mockito.when(gateway.saveProduct(Mockito.any(Product.class)))
                .thenReturn(Mono.error(new Throwable(Integer.toString(
                        HttpURLConnection.HTTP_BAD_REQUEST))));

        var result = useCase.apply(product);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(
                                HttpURLConnection.HTTP_BAD_REQUEST)))
                .verify();

        Mockito.verify(gateway, Mockito.times(1)).saveProduct(product);

    }
}