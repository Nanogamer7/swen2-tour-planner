package at.fhtw.tourplanner.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/geocode")
@RequiredArgsConstructor
@Validated
public class SearchController {
    //private final SearchService searchService;


    public ResponseEntity<?> getLocation(/*@RequestParam String searchText*/) {
        /*
        TODO: Implement search/autocomplete function according to OpenRouteService and forward
        https://openrouteservice.org/dev/#/api-docs/geocode/search/get
         */
        return null;
    }
}
