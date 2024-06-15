package org.example.frontend.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpHandler;
import org.example.frontend.data.models.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public final class TourRepository {
    // temporary singleton until REST server to have singular source for data

    private static TourRepository INSTANCE;
    private TourRepository() {
    }

    public static TourRepository getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new TourRepository();
        }

        return INSTANCE;
    }
    // end of singleton stuff

    // TODO: return if success
    public Tour createTour(TourInput tourInput) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<String> response;
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/tours/")) //TODO: config file for backend address
                    .POST(HttpRequest.BodyPublishers.ofString(JsonMapper.builder().build().writeValueAsString(tourInput)))
                    .header("Content-Type", "application/json; charset=utf-8")
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return JsonMapper.builder().build().readValue(response.body(), Tour.class);
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handling
        }

        return null;
    }

    // TODO: return if success
    public Tour modifyTour(TourInput tourInput, UUID tourUUID) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<String> response;
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/tours/" + tourUUID)) //TODO: config file for backend address
                    .PUT(HttpRequest.BodyPublishers.ofString(JsonMapper.builder().build().writeValueAsString(tourInput)))
                    .header("Content-Type", "application/json; charset=utf-8")
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return JsonMapper.builder().build().readValue(response.body(), Tour.class);
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handling
        }

        return null;
    }

    // TODO: return if success
    public void delete(UUID tourUUID) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<String> response;
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/tours/" + tourUUID)) //TODO: config file for backend address
                    .DELETE()
                    .header("Content-Type", "application/json; charset=utf-8")
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handling
        }
    }

    // TODO: return if success
    public TourLog addTourLog(TourLogInput tourLogInput, UUID tourUuid) {

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<String> response;
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/tours/" + tourUuid + "/logs/")) //TODO: config file for backend address
                    .POST(HttpRequest.BodyPublishers.ofString(JsonMapper.builder().addModule(new JavaTimeModule()).build().writeValueAsString(tourLogInput)))
                    .header("Content-Type", "application/json; charset=utf-8")
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return JsonMapper.builder().addModule(new JavaTimeModule()).build().readValue(response.body(), TourLog.class);
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handling
        }

        return null;
    }

    public List<Tour> fetchTours() {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<String> response;
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/tours/")) //TODO: config file for backend address
                    .GET()
                    .header("Content-Type", "application/json; charset=utf-8")
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return List.of(JsonMapper.builder().build().readValue(response.body(), Tour[].class));
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handling
        }

        return List.of();
    }

    public String fetchTourImage(UUID uuid){
        return "https://i.pinimg.com/originals/59/54/b4/5954b408c66525ad932faa693a647e3f.jpg";
    }

    public List<TourLog> fetchTourLogs(UUID uuid){
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<String> response;
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/tours/" + uuid + "/logs/")) //TODO: config file for backend address
                    .GET()
                    .header("Content-Type", "application/json; charset=utf-8")
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return List.of(JsonMapper.builder().addModule(new JavaTimeModule()).build().readValue(response.body(), TourLog[].class));
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handling
        }

        return List.of();
    }
}
