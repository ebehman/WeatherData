package rest.impl;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import rest.Weather;


import java.util.List;

public interface WeatherRepository extends CassandraRepository<Weather,String> {
    @Query("truncate table \"Weather\"")
    void truncate();
    void insert();
    List<Weather> findAllByUserId(String userId);
    List<Weather> findAllByPostalCode(Integer code);
}
