package rest.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
@ApiModel(value = "QueryWeatherUserOutput")
public class QueryWeatherUserOutput {
    private List<QueryWeatherOutput> weatherOutputList;
    private String message;
}
