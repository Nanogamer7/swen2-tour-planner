package at.fhtw.tourplanner;

import at.fhtw.tourplanner.models.Tour;
import at.fhtw.tourplanner.models.TourLog;
import at.fhtw.tourplanner.services.TourController;
import at.fhtw.tourplanner.services.TourLogService;
import at.fhtw.tourplanner.services.TourService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TourController.class)
class TourControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TourService tourService;

    @MockBean
    private TourLogService tourLogService;

    @Test
    void testGetAllTours() throws Exception {
        List<Tour> tours = new ArrayList<>();
        tours.add(new Tour());
        tours.add(new Tour());

        when(tourService.getAllTours()).thenReturn(tours);

        mockMvc.perform(get("/tours/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }


    @Test
    void testGetTourById() throws Exception {
        UUID id = UUID.randomUUID();
        Tour tour = new Tour();
        tour.setId(id);

        when(tourService.getTourById(id)).thenReturn(tour);

        mockMvc.perform(get("/tours/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }


    @Test
    void testDeleteTour() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/tours/" + id))
                .andExpect(status().isOk());

        verify(tourService).deleteTourById(id);
    }

    @Test
    void testCreateTour() throws Exception {
        Tour tour = new Tour();
        tour.setName("New Tour");
        Tour createdTour = new Tour();
        createdTour.setId(UUID.randomUUID());
        createdTour.setName("New Tour");

        when(tourService.createTour(tour)).thenReturn(createdTour);

        mockMvc.perform(post("/tours/")
                        .contentType("application/json")
                        .content("{\"name\": \"New Tour\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Tour"));
    }

    @Test
    void testUpdateTour() throws Exception {
        UUID id = UUID.randomUUID();
        Tour tour = new Tour();
        tour.setId(id);
        tour.setName("Updated Tour");

        when(tourService.updateTour(tour)).thenReturn(tour);

        mockMvc.perform(put("/tours/" + id)
                        .contentType("application/json")
                        .content("{\"name\": \"Updated Tour\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Tour"));
    }

    @Test
    void testGetTourLogs() throws Exception {
        UUID id = UUID.randomUUID();
        List<TourLog> tourLogs = new ArrayList<>();
        tourLogs.add(new TourLog());
        tourLogs.add(new TourLog());

        when(tourLogService.findAllOfTour(id)).thenReturn(tourLogs);

        mockMvc.perform(get("/tours/" + id + "/logs/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testCreateTourLog() throws Exception {
        UUID tourId = UUID.randomUUID();
        TourLog tourLog = new TourLog();
        tourLog.setId(UUID.randomUUID());

        when(tourLogService.create(any(TourLog.class), any(UUID.class))).thenReturn(tourLog);

        mockMvc.perform(post("/tours/" + tourId + "/logs/")
                        .contentType("application/json")
                        .content("{\"id\": \"" + tourLog.getId() + "\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(tourLog.getId().toString()));
    }

    @Test
    void testGetReport() throws Exception {
        UUID tourId = UUID.randomUUID();
        byte[] pdfReport = new byte[]{};

        when(tourService.generateReport(tourId)).thenReturn(pdfReport);

        mockMvc.perform(get("/tours/" + tourId + "/report"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/pdf"));
    }

    @Test
    void testGetRoute() throws Exception {
        UUID tourId = UUID.randomUUID();
        String routeInfo = "{\"route\":\"info\"}";

        when(tourService.getTourRouteInformationById(tourId)).thenReturn(routeInfo);

        mockMvc.perform(get("/tours/" + tourId + "/route"))
                .andExpect(status().isOk())
                .andExpect(content().json(routeInfo));
    }
}


