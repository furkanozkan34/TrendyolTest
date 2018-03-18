package trendyol.documents;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Document(collection = "product")
public class Product {

    @Id
    @ApiModelProperty(value = "Ürün id sini belirtir", example = "1", required = true)
    private Long id;

    @ApiModelProperty(value = "Ürünün adını belirtir", example = "Aypon 20S Plus.", required = true)
    private String name;

    @ApiModelProperty(value = "Ürün kategori id sini belirtir", example = "5", required = true)
    private Long categoryId;

    @ApiModelProperty(value = "Ürün fiyatını", example = "599.99", required = true)
    private BigDecimal price;

}
