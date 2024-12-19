package rest.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@NoArgsConstructor
@ToString
@ApiModel(value = "DeleteUserInput")
public class DeleteUserInput {
    @ApiModelProperty(dataType = "List", required = true, example = "Rpd1,Rpd2,Rpd3")
    private List<String> userIds;
}
