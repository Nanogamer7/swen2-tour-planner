package at.fhtw.tourplanner.data.ors;

import at.fhtw.tourplanner.TransportType;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class DirectionService {
    @Value("${openroute-service.api.key}")
    private String apiKey;

    @Value("${openroute-service.api.endpoint}")
    private String apiURL;

    public Integer getTime(TransportType type, double startLatitude, double startLongitude, double endLatitude, double endLongitude) throws IOException, InterruptedException {
        return (int) getJSson(type, startLatitude, startLongitude, endLatitude, endLongitude)
                .getJSONArray("routes")
                .getJSONObject(0)
                .getJSONObject("summary")
                .getDouble("duration");
    }

    public Integer getDistance(TransportType type, double startLatitude, double startLongitude, double endLatitude, double endLongitude) throws IOException, InterruptedException {
        return (int) getJSson(type, startLatitude, startLongitude, endLatitude, endLongitude)
                .getJSONArray("routes")
                .getJSONObject(0)
                .getJSONObject("summary")
                .getDouble("distance");
    }

    public Pair<Integer, Integer> getTimeDistance(TransportType type, double startLatitude, double startLongitude, double endLatitude, double endLongitude) throws IOException, InterruptedException {
        JSONObject summary = getJSson(type, startLatitude, startLongitude, endLatitude, endLongitude)
                .getJSONArray("routes")
                .getJSONObject(0)
                .getJSONObject("summary");
        return Pair.of((int) summary.getDouble("duration"), (int) summary.getDouble("distance"));
    }

    private JSONObject getJSson(TransportType type, double startLatitude, double startLongitude, double endLatitude, double endLongitude) throws IOException, InterruptedException {
        JSONArray coordinates = new JSONArray().put(
                new JSONArray()
                        .put(startLongitude)
                        .put(startLatitude)
        ).put(
                new JSONArray()
                        .put(endLongitude)
                        .put(endLatitude)
        );

        HttpResponse<String> response;
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(
                            UriComponentsBuilder.fromHttpUrl(apiURL)
                                    .path("/v2/directions/{type}/json")
                                    .buildAndExpand(type.apiString)
                                    .toUri()
                    )
                    .header("Authorization", apiKey)
                    .header("Content-Type", "application/json; charset=utf-8")
                    .POST(HttpRequest.BodyPublishers.ofString(
                            new JSONObject().put("coordinates", coordinates).toString()
                    ))
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        JSONObject geojson = new JSONObject(response.body());
        return geojson;
    }
}
