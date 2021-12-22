import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;

public class RequestWhether {
    private static final String API_KEY = "XPGM2ag3MAebttkTxxGsti2kzN77y3yt";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static  final OkHttpClient okHttpClient = new OkHttpClient();

    public static String sendCityIdRequest (String cityName) throws IOException {
        String cityId;
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("dataservice.accuweather.com")
                .addPathSegment("locations")
                .addPathSegment("v1")
                .addPathSegment("cities")
                .addPathSegment("search")
                .addQueryParameter("apikey",API_KEY)
                .addQueryParameter("q",cityName)
                .build();

        Request request = new Request.Builder()
                .addHeader("accept","application/json")
                .url(httpUrl)
                .build();

        Response response = okHttpClient.newCall(request).execute();

        String responseJson = response.body().string();

        JsonNode cityIdNode = objectMapper
                .readTree(responseJson)
                .at("/0/Key");
        cityId = cityIdNode.asText();

        return cityId;
    }
    public static String sendTextRequest (String cityId) throws IOException {
        String whetherText;
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("dataservice.accuweather.com")
                .addPathSegment("forecasts")
                .addPathSegment("v1")
                .addPathSegment("daily")
                .addPathSegment("1day")
                .addPathSegment(cityId)
                .addQueryParameter("apikey",API_KEY)
                .build();

        Request request = new Request.Builder()
                .addHeader("accept","application/json")
                .url(httpUrl)
                .build();

        Response response = okHttpClient.newCall(request).execute();

        String responseJson = response.body().string();


        JsonNode textNode = objectMapper
                .readTree(responseJson)
                .at("/DailyForecasts/0/Day")
                .get("IconPhrase");
        whetherText = textNode.asText();

        return whetherText;
    }

    public static int sendTempRequest (String cityId) throws IOException {
        int temperature;
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("dataservice.accuweather.com")
                .addPathSegment("forecasts")
                .addPathSegment("v1")
                .addPathSegment("daily")
                .addPathSegment("1day")
                .addPathSegment(cityId)
                .addQueryParameter("apikey",API_KEY)
                .build();

        Request request = new Request.Builder()
                .addHeader("accept","application/json")
                .url(httpUrl)
                .build();

        Response response = okHttpClient.newCall(request).execute();

        String responseJson = response.body().string();

        JsonNode tempNode = objectMapper
                .readTree(responseJson)
                .at("/DailyForecasts/0/Temperature")
                .get("Minimum")
                .get("Value");
        temperature = tempNode.asInt();

        return (temperature - 32) * 5/9;
    }

}
/*
JsonNode child = jsonNode.get("nestedObject");
    JsonNode childField = child.get("field");
    String field = childField.asText();
    System.out.println("field = " + field);
{
  "Headline": {
    "EffectiveDate": "2021-12-22T19:00:00+03:00",
    "EffectiveEpochDate": 1640188800,
    "Severity": 3,
    "Text": "Hypothermia likely without protective clothing Wednesday evening through Thursday afternoon",
    "Category": "cold",
    "EndDate": "2021-12-23T19:00:00+03:00",
    "EndEpochDate": 1640275200,
    "MobileLink": "http://www.accuweather.com/en/ru/nizhny-novgorod/9-294199_1_al/daily-weather-forecast/9-294199_1_al?lang=en-us",
    "Link": "http://www.accuweather.com/en/ru/nizhny-novgorod/9-294199_1_al/daily-weather-forecast/9-294199_1_al?lang=en-us"
  },
  "DailyForecasts": [
    {
      "Date": "2021-12-22T07:00:00+03:00",
      "EpochDate": 1640145600,
      "Temperature": {
        "Minimum": {
          "Value": -24,
          "Unit": "F",
          "UnitType": 18
        },
        "Maximum": {
          "Value": -5,
          "Unit": "F",
          "UnitType": 18
        }
      },
      "Day": {
        "Icon": 31,
        "IconPhrase": "Cold",
        "HasPrecipitation": false
      },
      "Night": {
        "Icon": 31,
        "IconPhrase": "Cold",
        "HasPrecipitation": false
      },
      "Sources": [
        "AccuWeather"
      ],
      "MobileLink": "http://www.accuweather.com/en/ru/nizhny-novgorod/9-294199_1_al/daily-weather-forecast/9-294199_1_al?day=1&lang=en-us",
      "Link": "http://www.accuweather.com/en/ru/nizhny-novgorod/9-294199_1_al/daily-weather-forecast/9-294199_1_al?day=1&lang=en-us"
    }
  ]
}
 */
