package rest.impl;

import rest.model.QueryWeatherOutput;
import rest.model.QueryWeatherPostalOutput;
import rest.model.QueryWeatherUserOutput;

public interface WeatherAppService {

    public void addUser(String userId,String key);
    public boolean checkUser(String userId);
    public void clearUser(String userId);
    public void getWeatherUpdate(String userID,Integer code, QueryWeatherOutput queryWeatherOutput);
    public void getAllWeatherUpdatePostal(Integer postalcode, QueryWeatherPostalOutput queryWeatherPostalOutput);
    public void getAllWeatherUpdateUser(String userId, QueryWeatherUserOutput queryWeatherUserOutput);
}
