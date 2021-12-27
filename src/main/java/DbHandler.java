import org.sqlite.JDBC;

import java.sql.*;
import java.util.ArrayList;

public class DbHandler {

    private final String PATH_TO_BD = "jdbc:sqlite:src/main/resources/Weather.db";
    private Connection connection;


    public DbHandler () throws SQLException {
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(PATH_TO_BD);

    }
    public void addWeather (WeatherToBd weatherToBd) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(
                  "INSERT INTO weathers ('city','localDate','weatherText','temperature') VALUES (?,?,?,?)"
            )){
            preparedStatement.setObject(1,weatherToBd.getCity());
            preparedStatement.setObject(2,weatherToBd.getLocalDate());
            preparedStatement.setObject(3,weatherToBd.getWeatherText());
            preparedStatement.setObject(4,weatherToBd.getTemperature());
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<WeatherToBd> selectAllWeather () {
        ArrayList<WeatherToBd> result = new ArrayList<>();
        try (Statement statement = this.connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM weathers");
            while (resultSet.next()) {
                WeatherToBd weatherToBd = new WeatherToBd(
                        resultSet.getString("city"),
                        resultSet.getString("localDate"),
                        resultSet.getString("weatherText"),
                        resultSet.getInt("temperature")
                ); result.add(weatherToBd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public void deleteWeather (int id) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(
                "DELETE FROM weathers WHERE id = ?"
        )){
            preparedStatement.setObject(1, id );
            preparedStatement.execute();

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
