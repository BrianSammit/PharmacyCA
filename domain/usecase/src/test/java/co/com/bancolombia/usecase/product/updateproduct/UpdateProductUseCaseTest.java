package co.com.bancolombia.usecase.product.updateproduct;


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
class UpdateProductUseCaseTest {

    @Mock
    ProductGateway repository;

    UpdateProductUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpdateProductUseCase(repository);
    }

    @Test
    @DisplayName("Update product success")
    void UpdateProduct() {
        var product = new Product(
                "testId",
                "test name",
                "test description",
                5.22, "img test",
                "test category");

        var updatedProduct = new Product(
                "testId",
                "test name updated",
                "test description updated",
                5.22, "img test",
                "test category updated");

        Mockito.when(repository.updateProduct("testId", updatedProduct))
                .thenReturn(Mono.just(updatedProduct));

        var result = useCase.apply("testId", updatedProduct);

        StepVerifier.create(result)
                .expectNext(updatedProduct)
                .expectComplete()
                .verify();

        Mockito.verify(repository, Mockito.times(1))
                .updateProduct("testId",updatedProduct);
    }

    @Test
    @DisplayName("Update product failed")
    void UpdateProduct_Failed() {
        var product = new Product(
                "testId",
                "test name",
                "test description",
                5.22, "img test",
                "test category");

        var updatedProduct = new Product(
                "testId",
                "test name updated",
                "test description updated",
                5.22, "img test",
                "test category updated");

        Mockito.when(repository.updateProduct(Mockito.any(String.class), Mockito.any(Product.class)))
                .thenReturn(Mono.error(new Throwable(Integer.toString(
                        HttpURLConnection.HTTP_BAD_REQUEST))));

        var result = useCase.apply("testId", updatedProduct);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(
                                HttpURLConnection.HTTP_BAD_REQUEST)))
                .verify();

        Mockito.verify(repository, Mockito.times(1))
                .updateProduct("testId", updatedProduct);
    }
}
