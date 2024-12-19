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
public class QueryWeatherOutput {
    @ApiModelProperty(dataType = "Integer", required = true, example = "24")
    private String temperature;
    private String precipitation;
    private String humdity;
    private String wind;

    private String message;
}
