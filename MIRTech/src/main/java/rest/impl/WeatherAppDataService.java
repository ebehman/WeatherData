package rest.impl;

import rest.Weather;


import java.util.List;

public interface WeatherAppDataService {

    public boolean checkUser(String id);
    public void insertUser(String id,String key);
    public void removeUser(String id);
    public void addWeather(Weather weather);
    public List<Weather> queryPostalcodeWeather(Integer code);
    public List<Weather> queryUserWeather(String userId);
}
