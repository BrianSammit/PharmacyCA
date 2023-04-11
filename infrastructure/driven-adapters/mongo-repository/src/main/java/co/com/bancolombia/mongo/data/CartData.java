package co.com.bancolombia.mongo.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Data
@Document(collection = "cart")
@NoArgsConstructor
public class CartData {

    @Id
    private String id = UUID.randomUUID().toString().substring(0,10);
    private Set<ProductData> products = new HashSet<>();
    private Double totalPrice;

    public CartData(Double totalPrice) {
        if (this.products.isEmpty()){
            this.totalPrice = 0.0;
        } else {
            this.products.stream().forEach(product -> this.totalPrice += product.getPrice());
        }

    }
}
