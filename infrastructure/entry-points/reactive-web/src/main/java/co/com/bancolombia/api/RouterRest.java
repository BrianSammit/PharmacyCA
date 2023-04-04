package co.com.bancolombia.api;

import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.usecase.saveproduct.SaveProductUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    @RouterOperation(path = "/products", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = SaveProductUseCase.class, method = RequestMethod.POST,
            beanMethod = "apply",
            operation = @Operation(operationId = "saveProducts", tags = "Product usecases",
                    responses = {
                            @ApiResponse(responseCode = "201", description = "Success",
                                    content = @Content(schema = @Schema(implementation = Product.class))),
                            @ApiResponse(responseCode = "406", description = "Not acceptable, Try again")
                    }))
    public RouterFunction<ServerResponse> saveProduct(SaveProductUseCase saveProductUseCase){
        return route(POST("/products").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Product.class)
                        .flatMap(product -> saveProductUseCase.apply(product)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(throwable.getMessage())))
        );
    }
}
