package at.fhtw.tourplanner.services;

import at.fhtw.tourplanner.data.ors.DirectionService;
import at.fhtw.tourplanner.models.Tour;
import at.fhtw.tourplanner.models.TourLog;
import at.fhtw.tourplanner.data.repositories.TourRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourService {
    private final TourRepository tourRepository;
    private final DirectionService directionService;
    private final TourLogService tourLogService;
    private static final Logger logger = LogManager.getLogger(TourService.class);

    public List<Tour> getAllTours() {
        return tourRepository.findAll().stream().peek(
                this::addTimeDistanceInfo
        ).collect(Collectors.toList());
    }

    public Tour getTourById(UUID id) {
        return tourRepository.findById(id).orElse(null);
    }

    public Tour createTour(Tour tour) {
        return addTimeDistanceInfo(tourRepository.save(tour));
    }

    private Tour addTimeDistanceInfo(Tour tour) {
        try {
            Pair<Integer, Integer> timeDist = directionService.getTimeDistance(
                    tour.getType(),
                    tour.getStart_lat(),
                    tour.getStart_long(),
                    tour.getEnd_lat(),
                    tour.getEnd_long()
            );
            tour.setTime(timeDist.getFirst());
            tour.setDistance(timeDist.getSecond());
        } catch (Exception e) {
            e.printStackTrace(); // TODO: logging here
            // do nothing
        }
        return tour;
    }

    public Tour updateTour(Tour tour) {
        // TODO: conditionally invalidate tour logs
        return tourRepository.save(tour);
    }

    public void deleteTourById(UUID id) {
        tourRepository.deleteById(id);
    }

    public byte[] generateReport(UUID id) {
        Tour tour = getTourById(id);
        List<TourLog> tourLogs = tourLogService.findAllOfTour(id);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);


        document.add(new Paragraph("Tour Report").setFontSize(18).setBold());
        document.add(new Paragraph("Tour Name: " + tour.getName()));
        document.add(new Paragraph("Description: " + tour.getDescription()));
        document.add(new Paragraph("Start Location: (" + tour.getStart_lat() + ", " + tour.getStart_long() + ")"));
        document.add(new Paragraph("End Location: (" + tour.getEnd_lat() + ", " + tour.getEnd_long() + ")"));


        float[] columnWidths = {1, 2, 2, 2, 2};
        Table table = new Table(columnWidths);
        table.addHeaderCell(new Cell().add(new Paragraph("Timestamp")));
        table.addHeaderCell(new Cell().add(new Paragraph("Difficulty")));
        table.addHeaderCell(new Cell().add(new Paragraph("Distance")));
        table.addHeaderCell(new Cell().add(new Paragraph("Time")));
        table.addHeaderCell(new Cell().add(new Paragraph("Rating")));

        for (TourLog log : tourLogs) {
            table.addCell(new Cell().add(new Paragraph(log.getTimestamp().toString())));
            table.addCell(new Cell().add(new Paragraph(log.getDifficulty().toString())));
            table.addCell(new Cell().add(new Paragraph(log.getDistance().toString())));
            table.addCell(new Cell().add(new Paragraph(log.getTime().toString())));
            table.addCell(new Cell().add(new Paragraph(log.getRating().toString())));
        }

        document.add(table);
        document.close();

        logger.info("PDF successfully created for Tour ID: {}", id);

        return baos.toByteArray();
    }

    public String getTourRouteInformationById(UUID id) {
        Tour tour = getTourById(id);
        try {
            return directionService.getJson(tour.getType(), tour.getStart_lat(), tour.getStart_long(), tour.getEnd_lat(), tour.getEnd_long(), true).toString();
        } catch (Exception e) {
            e.printStackTrace();
            // http 500 do nothing
        }

        return "";
    }
}