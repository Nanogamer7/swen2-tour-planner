package at.fhtw.tourplanner;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PointConverter implements AttributeConverter<Point, String> {

    @Override
    public String convertToDatabaseColumn(Point point) {
        if (point == null) {
            return null;
        }
        return "(" + point.x() + "," + point.y() + ")";
    }

    @Override
    public Point convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        String[] parts = dbData.replace("(", "").replace(")", "").split(",");
        return new Point(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]));
    }
}