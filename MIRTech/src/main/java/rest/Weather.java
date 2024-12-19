package rest;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data
@NoArgsConstructor
@ToString
@Table
public class Weather
{    @PrimaryKey
    private UUID id;
    private String user;
    private Integer postalcode;
    private String temperature;
    private String precipitation;
    private String humdity;
    private String wind;
    private String timestamp;

}
