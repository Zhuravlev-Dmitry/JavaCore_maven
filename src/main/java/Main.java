import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        String CITY = "Nizhniy Novgorod";
        String WEATHER_TEXT;
        int TEMPERATURE;
        String cityId;

        Date date = new Date();
        String DATA = String.valueOf(date);

        cityId =  RequestWeather.sendCityIdRequest(CITY);
        System.out.println("Уникальный ключ города " + CITY +" : " + cityId);

        WEATHER_TEXT = RequestWeather.sendTextRequest(cityId);
        TEMPERATURE = RequestWeather.sendTempRequest(cityId);

        System.out.printf("В городе " + CITY + " на дату " + date + " ожидается " + WEATHER_TEXT +
                ", температура до : " + TEMPERATURE + " C");

        WeatherToBd weatherToBd = new WeatherToBd(CITY,DATA,WEATHER_TEXT,TEMPERATURE);

        DbHandler dbHandler = new DbHandler();

        dbHandler.addWeather(weatherToBd);

    //    dbHandler.deleteWeather(3);

        System.out.println();

        System.out.println("Чтение всех данных из базы данных : ");

        System.out.println(dbHandler.selectAllWeather());

    }
}
