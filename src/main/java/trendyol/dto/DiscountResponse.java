package trendyol.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DiscountResponse extends DiscountRequest {

    private BigDecimal discountedPrice;
}
