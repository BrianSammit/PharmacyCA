package co.com.bancolombia.api;

import co.com.bancolombia.model.cart.Cart;
import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.usecase.cart.addproducttocart.AddProductToCartUseCase;
import co.com.bancolombia.usecase.cart.deletecart.DeleteCartUseCase;
import co.com.bancolombia.usecase.cart.getcartbyid.GetCartByIdUseCase;
import co.com.bancolombia.usecase.cart.removeproductfromcart.RemoveProductFromCartUseCase;
import co.com.bancolombia.usecase.cart.savecart.SaveCartUseCase;
import co.com.bancolombia.usecase.cart.updatecart.UpdateCartUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRestCart {
    private WebClient productAPI;

    public RouterRestCart() { productAPI = WebClient.create("http://localhost:8080");}

    @Bean
    @RouterOperation(path = "/carts/{cartId}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetCartByIdUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "getCartById", tags = "Cart usecases",
                    parameters = {@Parameter(name = "cartId", description = "cart Id", required= true, in = ParameterIn.PATH)},
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Cart.class))),
                            @ApiResponse(responseCode = "404", description = "Not Found")
                    }))
    public RouterFunction<ServerResponse> getCartById (GetCartByIdUseCase getCartByIdUseCase){
        return route(GET("/carts/{cartId}"),
                request -> getCartByIdUseCase.apply(request.pathVariable("cartId"))
                        .flatMap(cart -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(cart))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );
    }

    @Bean
    @RouterOperation(path = "/carts", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = SaveCartUseCase.class, method = RequestMethod.POST,
            beanMethod = "apply",
            operation = @Operation(operationId = "saveCart", tags = "Cart usecases",
                    parameters = {@Parameter(name = "cart", in = ParameterIn.PATH,
                            schema = @Schema(implementation = Cart.class))},
                    responses = {
                            @ApiResponse(responseCode = "201", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Cart.class))),
                            @ApiResponse(responseCode = "406", description = "Not acceptable, Try again")
                    },
                    requestBody = @RequestBody(required = true, description = "Save a Cart following the schema",
                            content = @Content(schema = @Schema(implementation = Cart.class)))
            ))
    public RouterFunction<ServerResponse> saveCart (SaveCartUseCase saveCartUseCase){
        return route(POST("/carts").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Cart.class)
                        .flatMap(cart -> saveCartUseCase.apply(cart)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                                        .bodyValue(throwable.getMessage()))));
    }

    @Bean
    @RouterOperation(path = "/carts/{cartId}/addProduct/{productId}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = AddProductToCartUseCase.class, method = RequestMethod.POST,
            beanMethod = "apply",
            operation = @Operation(operationId = "addProductToListInCart", tags = "Cart usecases",
                    parameters = {@Parameter(name = "cartId", description = "Cart Id", required= true, in = ParameterIn.PATH),
                            @Parameter(name = "productId", description = "Product Id", required= true, in = ParameterIn.PATH)},
                    responses = {
                            @ApiResponse(responseCode = "201", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Cart.class))),
                            @ApiResponse(responseCode = "406", description = "Not acceptable, Try again")
                    }
            ))
    public RouterFunction<ServerResponse> addProductToList (AddProductToCartUseCase addProductToCartUseCase){
        return route(POST("/carts/{cartId}/addProduct/{productId}"),
                request -> productAPI.get()
                        .uri("/products/"+request.pathVariable("productId"))
                        .retrieve()
                        .bodyToMono(Product.class)
                        .flatMap(Product -> addProductToCartUseCase
                                .apply(request.pathVariable("cartId"), Product)
                                .flatMap(cart -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(cart))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                                        .bodyValue(throwable.getMessage())))
        );
    }

    @Bean
    @RouterOperation(path = "/carts/{cartId}/removeProduct/{productId}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = RemoveProductFromCartUseCase.class, method = RequestMethod.POST,
            beanMethod = "apply",
            operation = @Operation(operationId = "removeItemFromListInCart", tags = "Cart usecases",
                    parameters = {@Parameter(name = "cartId", description = "Cart Id", required= true, in = ParameterIn.PATH),
                            @Parameter(name = "productId", description = "Product Id", required= true, in = ParameterIn.PATH)},
                    responses = {
                            @ApiResponse(responseCode = "201", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Cart.class))),
                            @ApiResponse(responseCode = "406", description = "Not acceptable, Try again")
                    }))
    public RouterFunction<ServerResponse> removeProductFromList (RemoveProductFromCartUseCase removeProductFromCartUseCase){
        return route(POST("/carts/{cartId}/removeProduct/{productId}"),
                request -> productAPI.get()
                        .uri("/products/"+request.pathVariable("productId"))
                        .retrieve()
                        .bodyToMono(Product.class)
                        .flatMap(product -> removeProductFromCartUseCase
                                .apply(request.pathVariable("cartId"), product)
                                .flatMap(cart -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(cart))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                                        .bodyValue(throwable.getMessage())))
        );
    }

    @Bean
    @RouterOperation(path = "/carts/{cartId}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = UpdateCartUseCase.class, method = RequestMethod.PUT,
            beanMethod = "apply",
            operation = @Operation(operationId = "updateCart", tags = "Cart usecases",
                    parameters = {@Parameter(name = "cartId", description = "Cart Id", required= true, in = ParameterIn.PATH),
                            @Parameter(name = "cart", in = ParameterIn.PATH,
                                    schema = @Schema(implementation = Cart.class))},
                    responses = {
                            @ApiResponse(responseCode = "201", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Cart.class))),
                            @ApiResponse(responseCode = "406", description = "Not acceptable, Try again")
                    },
                    requestBody = @RequestBody(required = true, description = "Save a cart following the schema",
                            content = @Content(schema = @Schema(implementation = Cart.class)))
            ))
    public RouterFunction<ServerResponse> updateCart (UpdateCartUseCase updateCartUseCase){
        return route(PUT("/carts/{cartId}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Cart.class)
                        .flatMap(cart -> updateCartUseCase.apply(request.pathVariable("cartId"),
                                        cart)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                                        .bodyValue(throwable.getMessage()))
                        )
        );
    }

    @Bean
    @RouterOperation(path = "/carts/{cartId}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = DeleteCartUseCase.class, method = RequestMethod.DELETE,
            beanMethod = "apply",
            operation = @Operation(operationId = "deleteCartById", tags = "Cart usecases",
                    parameters = {@Parameter(name = "cartId", description = "cart Id", required= true, in = ParameterIn.PATH)},
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Cart.class))),
                            @ApiResponse(responseCode = "404", description = "Not Found")
                    }))
    public RouterFunction<ServerResponse> deleteCart (DeleteCartUseCase deleteCartUseCase){
        return route(DELETE("/carts/{cartId}"),
                request -> deleteCartUseCase.apply(request.pathVariable("cartId"))
                        .thenReturn(ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue("Cart deleted"))
                        .flatMap(serverResponseMono -> serverResponseMono)
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );
    }
}
