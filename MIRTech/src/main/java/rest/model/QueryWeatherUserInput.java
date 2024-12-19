package rest.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@NoArgsConstructor
@ToString
@ApiModel(value = "QueryWeatherUserInput")
public class QueryWeatherUserInput {
    private String userId;
}
