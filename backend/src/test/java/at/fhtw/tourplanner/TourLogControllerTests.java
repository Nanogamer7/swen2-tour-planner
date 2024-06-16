package at.fhtw.tourplanner;


import at.fhtw.tourplanner.models.TourLog;
import at.fhtw.tourplanner.services.TourLogController;
import at.fhtw.tourplanner.services.TourLogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TourLogController.class)
class TourLogControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TourLogService tourLogService;

    @Test
    void testGetTourLogById() throws Exception {
        UUID id = UUID.randomUUID();
        TourLog tourLog = new TourLog();
        tourLog.setId(id);

        when(tourLogService.findById(id)).thenReturn(tourLog);

        mockMvc.perform(get("/logs/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    void testDeleteTourLogById() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/logs/" + id))
                .andExpect(status().isOk());

        verify(tourLogService).deleteById(id);
    }

    @Test
    void testUpdateTourLog() throws Exception {
        UUID id = UUID.randomUUID();
        TourLog tourLog = new TourLog();
        tourLog.setId(id);
        tourLog.setDifficulty(3);

        when(tourLogService.update(any(TourLog.class))).thenReturn(tourLog);

        mockMvc.perform(put("/logs/" + id)
                        .contentType("application/json")
                        .content("{\"difficulty\": 3}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.difficulty").value(3));
    }


}

