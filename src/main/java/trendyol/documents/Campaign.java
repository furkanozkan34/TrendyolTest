package trendyol.documents;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@ApiModel
@Document(collection = "campaign")
public class Campaign {
    @Id
    @ApiModelProperty(value = "Kampanya id sini belirtir", example = "1", required = true)
    private Long id;

    @ApiModelProperty(value = "Kampanya adını belirtir", example = "A kategorisinde patron çıldırı.", required = true)
    private String name;

    @ApiModelProperty(value = "Kategori id sini belirtir." +
            "Eğer bir kategori kampanyası oluşturulacaksa bu alan zorunludur. Diğer durumda null gönderilebilir", example = "1")
    private Long categoryId;

    @ApiModelProperty(value = "Ürün id sini belirtir." +
            "Eğer bir ürün kampanyası oluşturulacaksa bu alan zorunludur. Diğer durumda null gönderilebilir", example = "1")
    private Long productId;

    @ApiModelProperty(value = "Eğer bir TUTAR tipinde kampanya indirimi yapılacaksa, söz konusu indirimi belirtir." ,example = "99.99")
    private BigDecimal discountPrice;

    @ApiModelProperty(value = "Eğer bir ORAN tipinde kampanya indirimi yapılacaksa, söz konusu oranı belirtir." ,example = "10")
    private BigDecimal discountRate;

    @ApiModelProperty(value = "Kampanın ürün ya da kategori kampanyası olduğunu belirtir. True ise kategori false ise ürün kampanyasıdır" ,example = "false")
    private Boolean campaignType;  /*true = category,  false = product*/

    @ApiModelProperty(value = "İndirimin oran ya da tutar tipinde olduğunu belirtir. True ise oran false ise tutar tipindedir" ,example = "false")
    private Boolean discountType; /*true = rate, false = price*/

    @ApiModelProperty(value = "Eğer bir oran tipinde indirim yapılacak ise bu alana maksimum indirim tutarını girmek zorunludur. S" +
            "Sistem indirimi bu parametredeki değerin üstüne çıkartmayacak, maksimum bu değere setleyecektir" ,example = "150")
    private BigDecimal maxDiscountPrice;

}
