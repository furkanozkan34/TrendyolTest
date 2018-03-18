package trendyol.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel
public class DiscountRequest {

    @ApiModelProperty(value = "Sepetteki ürünün kategori id sini belirtir", example = "1", required = true)
    private Long categoryId;

    @ApiModelProperty(value = "Sepetteki ürünün id sini belirtir", example = "3", required = true)
    private Long productId;

    @ApiModelProperty(value = "Sepetteki ürünün fiyatını belirtir", example = "150.99", required = true)
    private BigDecimal price;
}
