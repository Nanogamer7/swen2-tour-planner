package org.example.frontend.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpHandler;
import org.example.frontend.data.models.*;
import org.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.net.http.HttpClient;

public final class TourRepository {
    // temporary singleton until REST server to have singular source for data

    private static TourRepository INSTANCE;
    private static final Logger logger = LogManager.getLogger(TourRepository.class);
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
            logger.error("Failed to create tour", e);
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
            logger.error("Failed to modify tour with ID: " + tourUUID, e);
        }

        return null;
    }

    // TODO: return if success
    public Tour modifyLog(TourLogInput tourLogInput, UUID logUUID) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<String> response;
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/logs/" + logUUID)) //TODO: config file for backend address
                    .PUT(HttpRequest.BodyPublishers.ofString(JsonMapper.builder().build().writeValueAsString(tourLogInput)))
                    .header("Content-Type", "application/json; charset=utf-8")
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return JsonMapper.builder().build().readValue(response.body(), Tour.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Failed to modify log with ID: " + logUUID, e);
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
            logger.info("Successfully deleted tour with ID: " + tourUUID);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Failed to delete tour with ID: " + tourUUID, e);
        }
    }

    // TODO: return if success
    public void deleteLog(UUID logUUID) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<String> response;
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/logs/" + logUUID)) //TODO: config file for backend address
                    .DELETE()
                    .header("Content-Type", "application/json; charset=utf-8")
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("Successfully deleted log with ID: " + logUUID);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Failed to delete log with ID: " + logUUID, e);
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
            logger.error("Failed to add tour log for tour with ID: " + tourUuid, e);
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
            logger.error("Failed to fetch tours", e);
        }

        return List.of();
    }

    public JSONObject getTourJSON(UUID tourUUID) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<String> response;
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/tours/" + tourUUID)) //TODO: config file for backend address
                    .GET()
                    .header("Content-Type", "application/json; charset=utf-8")
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return new JSONObject(response.body());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Failed to fetch tour JSON for tour with ID: " + tourUUID, e);
        }

        return null;
    }

     /**
      * Downloads the GeoJSON for the tour route. This geojson is saved to a directions.js file in /resources/,
       * which is ultimately used by our map.html to visually display our route.
       */
     public void downloadTourDirectionGeoJson(UUID uuid) { // TODO: this should return a JSONObject, do saving at appropriate place
         try (HttpClient client = HttpClient.newHttpClient()) {
             HttpResponse<String> response;
             HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/tours/" + uuid + "/route")) //TODO: config file for backend address
                     .GET()
                     .header("Content-Type", "application/json; charset=utf-8")
                     .build();

             response = client.send(request, HttpResponse.BodyHandlers.ofString());

             Path resourcesDirectory = Paths.get("src", "main", "resources", "org", "example", "frontend"); // hardcoded bad >:c
             Path outputPath = resourcesDirectory.resolve("directions.js");

             System.out.println("Downloading directions geojson.\nSaving it to %s".formatted(
                     outputPath.toAbsolutePath().toString())
             );

             // Write response to directions.js
             Files.writeString(outputPath, "var directions = " + response.body());
         } catch (Exception e) {
             e.printStackTrace();
             logger.error("Failed to download tour direction GeoJson for tour with ID: " + uuid, e);
         }
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
            logger.error("Failed to fetch tour logs for tour with ID: " + uuid, e);
        }

        return List.of();
    }

    public byte[] getPdfReport(UUID uuid) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<byte[]> response;
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/tours/" + uuid + "/report")) //TODO: config file for backend address
                    .GET()
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Failed to fetch PDF report for tour with ID: " + uuid, e);
        }

        return null;
    }
}
