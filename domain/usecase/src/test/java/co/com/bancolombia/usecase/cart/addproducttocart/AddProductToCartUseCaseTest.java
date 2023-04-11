package co.com.bancolombia.usecase.cart.addproducttocart;


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
class AddProductToCartUseCaseTest {

    @Mock
    CartGateway gateway;

    AddProductToCartUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new AddProductToCartUseCase(gateway);
    }

    @Test
    @DisplayName("Add product to cart success")
    void addItemToList(){

       var product = new Product(
                "testId",
                "test name",
                "test description",
                5.22, "img test",
                "test category");

        Set<Product> productList = new HashSet<>();
        var cart = new Cart("1",  productList, 0.0);

        var products = cart.getProducts();
        products.add(product);

        var updatedCart = new Cart("1", products, 58.45);

        Mockito.when(gateway.addProductToCart(Mockito.any(String.class), Mockito.any(Product.class)))
                .thenReturn(Mono.just(updatedCart));

        var result = useCase.apply("1", product);

        StepVerifier.create(result)
                .expectNext(updatedCart)
                .expectComplete()
                .verify();

        Mockito.verify(gateway, Mockito.times(1)).addProductToCart("1", product);
    }

    @Test
    @DisplayName("Add item to list failed")
    void addItemToList_Failed(){
        var product = new Product(
                "testId",
                "test name",
                "test description",
                5.22, "img test",
                "test category");

        String cartId = "1";

        Mockito.when(gateway.addProductToCart(Mockito.any(String.class), Mockito.any(Product.class)))
                .thenReturn(Mono.error(new Throwable(Integer.toString(
                        HttpURLConnection.HTTP_BAD_REQUEST))));

        var result = useCase.apply(cartId, product);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(
                                HttpURLConnection.HTTP_BAD_REQUEST)))
                .verify();

        Mockito.verify(gateway, Mockito.times(1)).addProductToCart(cartId, product);
    }

}
