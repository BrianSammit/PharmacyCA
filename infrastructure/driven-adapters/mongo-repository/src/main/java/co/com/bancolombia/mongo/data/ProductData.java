package co.com.bancolombia.mongo.data;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@NoArgsConstructor
@Document(collection = "products")
public class ProductData {
    @Id
    private String id ;
    @NotNull(message = "Name can't be null")
    @NotBlank(message = "Name can't be empty")
    private String name;

    @NotNull (message = "Description can't be null")
    @NotBlank(message = "Description can't be empty")
    private String description;

    @NotNull (message = "Price can't be null")
    @DecimalMin(value = "0.0", inclusive = false)
    private Double price;

    @NotNull(message = "img can't be null")
    @NotBlank(message = "img can't be empty")
    private String img;

    @NotNull(message = "Category can't be null")
    @NotBlank(message = "Category can't be empty")
    private String category;
//    private Boolean inStock = true;

    public ProductData(String id, String name, String description, Double price, String img, String category) {
        this.id = UUID.randomUUID().toString().substring(0, 10);
        this.name = name;
        this.description = description;
        this.price = price;
        this.img = img;
        this.category = category;
    }
}
