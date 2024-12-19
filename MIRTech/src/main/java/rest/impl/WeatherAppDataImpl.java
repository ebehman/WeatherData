package rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rest.Weather;


import java.util.List;

@Component
public class WeatherAppDataImpl implements WeatherAppDataService {
    @Autowired
    private WeatherRepository weatherRepository;
    @Autowired
    private UserRepositry userRepositry;

    @Override
    public boolean checkUser(String id) {
        return false;
    }

    @Override
    public void insertUser(String id, String key) {

    }

    @Override
    public void removeUser(String id) {

    }

    @Override
    public void addWeather(Weather weather) {
        weatherRepository.insert();
    }

    @Override
    public List<Weather> queryPostalcodeWeather(Integer code) {
        return weatherRepository.findAllByPostalCode(code);
    }

    @Override
    public List<Weather> queryUserWeather(String userId) {
        return weatherRepository.findAllByUserId(userId);
    }
}
