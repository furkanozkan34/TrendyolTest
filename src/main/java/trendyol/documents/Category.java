package trendyol.documents;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ApiModel
@Document(collection = "category")
public class Category {

    @Id
    @ApiModelProperty(value = "Kategorinin id sini belirtir", example = "1", required = true)
    private Long id;

    @ApiModelProperty(value = "Kategorinin adını belirtir", example = "Elektrikli Ev Aletleri", required = true)
    private String name;
}
