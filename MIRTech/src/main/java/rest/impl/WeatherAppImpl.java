package rest.impl;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import rest.Weather;
import rest.model.QueryWeatherOutput;
import rest.model.QueryWeatherPostalOutput;
import rest.model.QueryWeatherUserOutput;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class WeatherAppImpl implements WeatherAppService {
    @Autowired
    private WeatherAppDataService weatherAppDataService;

    @Override
    public void addUser(String userId, String key) {
        weatherAppDataService.insertUser(userId,key);
        System.out.println("Adding user to WeatherApp");
    }

    @Override
    public boolean checkUser(String userId) {
        return weatherAppDataService.checkUser(userId);

    }

    @Override
    public void clearUser(String userId) {
        weatherAppDataService.removeUser(userId);
        System.out.println("Removing user in WeatherApp");
    }

    @Override
    public void getWeatherUpdate(String userID,Integer code, QueryWeatherOutput queryWeatherOutput) {
        RestClient restClient = RestClient.create();
        ResponseEntity<String> result = restClient.get().
           uri("https://api.weatherapi.com/v1/current.json?q=10001&key=cd014e45c7814fed88891009241812")
                .retrieve().toEntity(String.class);

        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(result.getBody());
            JSONObject json1 = (JSONObject)parser.parse(json.get("current").toString());
            queryWeatherOutput.setTemperature(json1.get("temp_c").toString());
            queryWeatherOutput.setWind(json1.get("wind_kph").toString());
            queryWeatherOutput.setHumdity(json1.get("humidity").toString());
            queryWeatherOutput.setPrecipitation(json1.get("precip_mm").toString());
            queryWeatherOutput.setMessage("Weather Data successfully fetched");

            // insert the data to DB before sending result to rest layer
            Weather weather = new Weather();
            weather.setId(UUID.randomUUID());
            weather.setUser(userID);
            weather.setPostalcode(code);
            weather.setTemperature(json1.get("temp_c").toString());
            weather.setWind(json1.get("wind_kph").toString());
            weather.setHumdity(json1.get("humidity").toString());
            weather.setPrecipitation(json1.get("precip_mm").toString());
            weather.setTimestamp(LocalDateTime.now().toString());
            weatherAppDataService.addWeather(weather);
            System.out.println(weather.toString());

        } catch (Exception e) {
            queryWeatherOutput.setMessage("Exception in fetching the weather Data");
            throw new RuntimeException(e);
        }
        System.out.println(queryWeatherOutput.toString());
        System.out.println(result.getBody());
   }

    @Override
    public void getAllWeatherUpdatePostal(Integer postalcode, QueryWeatherPostalOutput queryWeatherPostalOutput) {
        List<Weather> weatherList= weatherAppDataService.queryPostalcodeWeather(postalcode);
        List<QueryWeatherOutput> queryWeatherOutputList = new ArrayList<>();
        for(Weather w : weatherList){
            QueryWeatherOutput queryWeatherOutput = new QueryWeatherOutput();
            queryWeatherOutput.setTemperature(w.getTemperature());
            queryWeatherOutput.setWind(w.getWind());
            queryWeatherOutput.setHumdity(w.getHumdity());
            queryWeatherOutput.setPrecipitation(w.getPrecipitation());
            queryWeatherOutputList.add(queryWeatherOutput);
        }
        queryWeatherPostalOutput.setWeatherOutputList(queryWeatherOutputList);
        queryWeatherPostalOutput.setMessage("Success PO : "+postalcode);
    }

    @Override
    public void getAllWeatherUpdateUser(String userId, QueryWeatherUserOutput queryWeatherUserOutput) {
        List<Weather> weatherList= weatherAppDataService.queryUserWeather(userId);
        List<QueryWeatherOutput> queryWeatherOutputList = new ArrayList<>();
        for(Weather w : weatherList){
            QueryWeatherOutput queryWeatherOutput = new QueryWeatherOutput();
            queryWeatherOutput.setTemperature(w.getTemperature());
            queryWeatherOutput.setWind(w.getWind());
            queryWeatherOutput.setHumdity(w.getHumdity());
            queryWeatherOutput.setPrecipitation(w.getPrecipitation());
            queryWeatherOutputList.add(queryWeatherOutput);
        }
        queryWeatherUserOutput.setWeatherOutputList(queryWeatherOutputList);
        queryWeatherUserOutput.setMessage("Success User : "+userId);
    }
}
