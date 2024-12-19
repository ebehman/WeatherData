package rest.model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@NoArgsConstructor
@ToString
@ApiModel(value = "QueryWeatherInput")
public class QueryWeatherInput {
    @ApiModelProperty(dataType = "Integer", required = true, example = "560078")
    private Integer postalCode;
    @ApiModelProperty(dataType = "String", required = true, example = "manas094")
    private String userId;

}
