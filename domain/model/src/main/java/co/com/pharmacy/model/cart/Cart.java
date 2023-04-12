package co.com.pharmacy.model.cart;
import co.com.pharmacy.model.product.Product;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Cart {
    private String id = UUID.randomUUID().toString().substring(0,10);
    private Set<Product> products = new HashSet<Product>();
    private Double totalPrice;
}
