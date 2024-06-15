package at.fhtw.tourplanner;

import at.fhtw.tourplanner.models.Tour;
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
}

