import java.io.IOException;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws IOException {
        String CITY = "Nizhniy Novgorod";
        String WEATHER_TEXT;
        int TEMPERATURE;
        String cityId;
        Date date = new Date();
        cityId =  RequestWhether.sendCityIdRequest(CITY);
        System.out.println("Уникальный ключ города " + CITY +" : " + cityId);
        WEATHER_TEXT = RequestWhether.sendTextRequest(cityId);
        TEMPERATURE = RequestWhether.sendTempRequest(cityId);
        System.out.printf("В городе " + CITY + " на дату " + date + " ожидается " + WEATHER_TEXT +
                ", температура до : " + TEMPERATURE + " C");
    }
}
