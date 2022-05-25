import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class Networking {
    private String apiKey = "8823c95dd63e46079a3170028221805";
    private String baseUrl = "http://api.weatherapi.com/v1";

    public Networking()
    {

    }

    public weatherInfo getWeather(String loc)
    {
        String end = "/current.json";
        String call = baseUrl + end + "?key=" + apiKey + "&q=" + loc;

        String res = makeAPICall(call);

        return parseWeatherInfoJSON(res);
    }

    private String makeAPICall(String url)
    {
        try {
            URI myUri = URI.create(url); // creates a URI object from the url string
            HttpRequest request = HttpRequest.newBuilder().uri(myUri).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private weatherInfo parseWeatherInfoJSON(String json)
    {
        JSONObject jsonObj = new JSONObject(json);
        JSONObject loca = jsonObj.getJSONObject("location");
        String name = loca.getString("name");
        JSONObject curr = jsonObj.getJSONObject("current");
        double tempC = curr.getDouble("temp_c");
        double tempF = curr.getDouble("temp_f");
        JSONObject cond = curr.getJSONObject("condition");
        String condText = cond.getString("text");
        String icon = cond.getString("icon");

        weatherInfo weather = new weatherInfo(name, condText, icon, tempF, tempC);

        return weather;

    }
}
