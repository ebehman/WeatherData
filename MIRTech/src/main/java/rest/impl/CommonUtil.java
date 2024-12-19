package rest.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {
    private static WeatherAppService weatherAppService;
    public static boolean hasSpecialChr(String input) {
        if (input != null) {
            Pattern special = Pattern.compile("[*,'\"]");
            Matcher hasSpecial = special.matcher(input);
            return hasSpecial.find();
        } else {
            return false;
        }
    }

    public static boolean validateUser(String userId) {
        return weatherAppService.checkUser(userId);
    }
}
