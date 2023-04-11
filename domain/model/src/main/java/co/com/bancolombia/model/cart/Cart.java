package co.com.bancolombia.model.cart;
import co.com.bancolombia.model.product.Product;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Cart {
    private String id = UUID.randomUUID().toString().substring(0,10);
    private List<Product> products = new ArrayList<Product>();
    private Double totalPrice;
}
