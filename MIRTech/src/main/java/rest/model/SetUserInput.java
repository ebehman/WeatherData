package rest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@ApiModel(value = "SetUserInput")
public class SetUserInput {
 @ApiModelProperty(dataType = "String", required = true, example = "Rpd")
private String userId;
 @ApiModelProperty(dataType = "String", required = true, example = "Rpd")
private String key;
}
