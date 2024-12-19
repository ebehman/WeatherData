package rest.model;
import java.util.List;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@NoArgsConstructor
@ToString
@ApiModel(value = "DeleteUserOutput")
public class DeleteUserOutput {
    private List<String> messages;
}
