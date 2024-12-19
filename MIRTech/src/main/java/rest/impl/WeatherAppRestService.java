package rest.impl;


import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.ws.rs.Produces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rest.model.*;

import java.util.ArrayList;
import java.util.List;
import java.net.HttpURLConnection;
import java.util.Arrays;

@Produces({"application/json", "application/xml"})
@CrossOrigin
@RestController
@ComponentScan(basePackages = {"rest.*"})
public class WeatherAppRestService {

    @Autowired
    public WeatherAppService weatherAppService;
/**
 * Rest API for set-user with json string
 */
@RequestMapping(value = Configuration.weatherAppv1 + "/set-user", method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Operation(summary = "API to set User version1", description = "Set User version1")
@ApiResponses(value = {
        @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Check Status",
                response = SetUserOutput.class)
})
 @ApiParam(value = "RPD Pairing information", required = true)
public ResponseEntity<SetUserOutput> setUser(@RequestBody SetUserInput input) {
    SetUserOutput setUserOutput = new SetUserOutput();
    if(input==null ||
            (input.getUserId()==null || input.getUserId().equalsIgnoreCase("")
            ) || (input.getKey()==null || input.getKey().equalsIgnoreCase("")
    )){
        setUserOutput.setMessage("UserId or Key can'nt be Null");
        return new ResponseEntity<>(setUserOutput, HttpStatus.OK);
    }
    if(CommonUtil.hasSpecialChr(input.getUserId())){
        setUserOutput.setMessage("UserId  can't have special characters");
        return new ResponseEntity<>(setUserOutput, HttpStatus.OK);
    }
   if(weatherAppService.checkUser(input.getUserId())){
    setUserOutput.setMessage("UserId already exists in WeatherApp");
    return new ResponseEntity<>(setUserOutput, HttpStatus.OK);
    }
    weatherAppService.addUser(input.getUserId(),input.getKey());
    setUserOutput.setMessage("UserId Created Sucessfully ! , Please qoute your userId in fetching " +
            "weather Data");
     return new ResponseEntity<>(setUserOutput, HttpStatus.OK);
}
    /**
     * Rest API for delete-user with json string
     */
    @RequestMapping(value = Configuration.weatherAppv1 + "/delete-user", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "API to delete user", description = "Delete user in WeatherApp")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Check Status",
                    response = DeleteUserOutput.class)
    })
    @ApiParam(value = "List of User Ids", required = true)
    public ResponseEntity<DeleteUserOutput> removeUserId(@RequestBody DeleteUserInput input) {
        DeleteUserOutput deleteUserOutput = new DeleteUserOutput();
        if(input==null || input.getUserIds().isEmpty()){
            deleteUserOutput.setMessages(Arrays.asList("Please provide a userId to remove"));
            return  new ResponseEntity<>(deleteUserOutput,HttpStatus.OK);
        }
        List<String> messages = new ArrayList<>();
        for(String s : input.getUserIds()){
            weatherAppService.clearUser(s);
            messages.add("User "+s+"removed from weartherApp sucessfully");
        }
        deleteUserOutput.setMessages(messages);
        return  new ResponseEntity<>(deleteUserOutput,HttpStatus.OK);
    }
 /**
 * Rest API for query-weather data [Current] in Json format, With User Validation
 */
 @RequestMapping(value = Configuration.weatherAppv1 + "/query-weather", method = RequestMethod.POST,
         consumes = MediaType.APPLICATION_JSON_VALUE,
         produces = MediaType.APPLICATION_JSON_VALUE)
 @Operation(summary = "API to query RPD pairing for given MAC Addresses", description = "Query RPD pairing for given MAC Addresses")
 @ApiResponses(value = {
         @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Check Status",
                 response = QueryWeatherOutput.class)
 })
 @ApiParam(value = "List of RPD MAC Addresses", required = true)
 public ResponseEntity<QueryWeatherOutput> queryWeatherData(@RequestBody QueryWeatherInput input) {
     QueryWeatherOutput queryWeatherOutput = new QueryWeatherOutput();
     if(input==null ||(input.getUserId()==null || input.getUserId().equalsIgnoreCase(""))
                    || CommonUtil.hasSpecialChr(input.getUserId())){
         queryWeatherOutput.setMessage("Invalid user Id");
         return new ResponseEntity<>(queryWeatherOutput, HttpStatus.OK);
     }

     if(!CommonUtil.validateUser(input.getUserId())){
         queryWeatherOutput.setMessage("UserId not in WeatherApp , Please SignUp");
         return new ResponseEntity<>(queryWeatherOutput, HttpStatus.OK);
     }
     weatherAppService.getWeatherUpdate(input.getUserId(),input.getPostalCode(),queryWeatherOutput);
     return new ResponseEntity<>(queryWeatherOutput, HttpStatus.OK);
 }
    /**
     * Rest API for query-weather data history per postalcode in Json format, With User Validation
     */
    @RequestMapping(value = Configuration.weatherAppv1 + "/query-weather", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "API to query RPD pairing for given MAC Addresses", description = "Query RPD pairing for given MAC Addresses")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Check Status",
                    response = QueryWeatherPostalOutput.class)
    })
    @ApiParam(value = "List of RPD MAC Addresses", required = true)
    public ResponseEntity<QueryWeatherPostalOutput> queryPostalCodeWeatherData(@RequestBody QueryWeatherPostalInput input) {
        QueryWeatherPostalOutput queryWeatherPostalOutput = new QueryWeatherPostalOutput();

        if(input==null ||(input.getPostalCode()==null || input.getPostalCode()==0 )){
            queryWeatherPostalOutput.setMessage("Invalid Postal code, please provide a valid postal code");
            return  new ResponseEntity<>(queryWeatherPostalOutput,HttpStatus.OK);
        }
        weatherAppService.getAllWeatherUpdatePostal(input.getPostalCode(),queryWeatherPostalOutput);
        queryWeatherPostalOutput.setMessage("Sucess");
        return new ResponseEntity<>(queryWeatherPostalOutput,HttpStatus.OK);
    }
    /**
     * Rest API for query-weather data history per user in Json format, With User Validation
     */
    @RequestMapping(value = Configuration.weatherAppv1 + "/query-weather", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "API to query RPD pairing for given MAC Addresses", description = "Query RPD pairing for given MAC Addresses")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Check Status",
                    response = QueryWeatherUserOutput.class)
    })
    @ApiParam(value = "List of RPD MAC Addresses", required = true)
    public ResponseEntity<QueryWeatherUserOutput> queryUserWeatherData(@RequestBody QueryWeatherUserInput input) {
        QueryWeatherUserOutput queryWeatherUserOutput = new QueryWeatherUserOutput();

        if(input==null ||(input.getUserId()==null || input.getUserId().isEmpty() || CommonUtil.hasSpecialChr(input.getUserId()) )){
            queryWeatherUserOutput.setMessage("Invalid UserId, please provide a valid postal code");
            return  new ResponseEntity<>(queryWeatherUserOutput,HttpStatus.OK);
        }
        if(CommonUtil.validateUser(input.getUserId())){
            queryWeatherUserOutput.setMessage("UserId not in WeatherApp , Please SignUp");
            return new ResponseEntity<>(queryWeatherUserOutput, HttpStatus.OK);
        }
        weatherAppService.getAllWeatherUpdateUser(input.getUserId(),queryWeatherUserOutput);
        queryWeatherUserOutput.setMessage("Sucess");
        return new ResponseEntity<>(queryWeatherUserOutput,HttpStatus.OK);
    }
 }
