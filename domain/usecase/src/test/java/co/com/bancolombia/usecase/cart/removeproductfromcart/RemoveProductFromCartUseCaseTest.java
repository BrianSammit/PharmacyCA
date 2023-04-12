package co.com.bancolombia.usecase.cart.removeproductfromcart;


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
class RemoveProductFromCartUseCaseTest {

    @Mock
    CartGateway gateway;

    RemoveProductFromCartUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new RemoveProductFromCartUseCase(gateway);
    }

    @Test
    @DisplayName("Remove product from list success")
    void removeItemFromList(){

        // items already added in cart
        var product1 =  new Product(
                "testId",
                "test name",
                "test description",
                5.22, "img test",
                "test category");

        var prouduct2 =  new Product(
                "testId2",
                "test name 2",
                "test description 2",
                5.22, "img test",
                "test category 2");

        Set<Product> listItems = new HashSet<>();
        var cart = new Cart("1", listItems, 0.0);

        var items = cart.getProducts();
        items.add(product1);
        items.add(prouduct2);
        cart.setProducts(items);

        var removedItem = new Product(
                "testId2",
                "test name 2",
                "test description 2",
                5.22, "img test",
                "test category 2");


        var cartUpdated = cart;
        var productsChanged = cartUpdated.getProducts();
        productsChanged.remove(removedItem);
        cartUpdated.setProducts(productsChanged);

        Mockito.when(gateway.removeProductFromCart("1", removedItem))
                .thenReturn(Mono.just(cartUpdated));

        var result = useCase.apply("1", removedItem);

        StepVerifier.create(result)
                .expectNext(cartUpdated)
                .expectComplete()
                .verify();

        Mockito.verify(gateway, Mockito.times(1))
                .removeProductFromCart("1",removedItem);
    }

    @Test
    @DisplayName("Remove product from list failed")
    void removeItemFromList_Failed(){
        var removedItem = new Product(
                "testId2",
                "test name 2",
                "test description 2",
                5.22, "img test",
                "test category 2");

        String cartId = "1";

        Mockito.when(gateway.removeProductFromCart(Mockito.any(String.class), Mockito.any(Product.class)))
                .thenReturn(Mono.error(new Throwable(Integer.toString(
                        HttpURLConnection.HTTP_BAD_REQUEST))));

        var result = useCase.apply(cartId, removedItem);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable != null &&
                        throwable.getMessage().equals(Integer.toString(
                                HttpURLConnection.HTTP_BAD_REQUEST)))
                .verify();

        Mockito.verify(gateway, Mockito.times(1)).removeProductFromCart(cartId, removedItem);

    }
}
