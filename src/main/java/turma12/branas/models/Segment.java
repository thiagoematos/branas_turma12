package turma12.branas.models;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import turma12.branas.exceptions.InvalidDateException;
import turma12.branas.exceptions.InvalidDistanceException;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import static java.util.Objects.isNull;
import static turma12.branas.controllers.DateFormatter.format;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Segment {
    private static final int BEGINNING_OF_NIGHT = 22;
    private static final int ENDING_OF_NIGHT = 6;

    LocalDateTime date;
    int distance;

    public static Segment makeSegment(
            final String date,
            final Integer distance
    ) throws InvalidDistanceException, InvalidDateException {
        if (isNull(distance) || distance <= 0) {
            throw new InvalidDistanceException(distance);
        }
        var formattedDate = format(date)
                .orElseThrow(() -> new InvalidDateException(date));
        return new Segment(formattedDate, distance);
    }

    public boolean isOvernight() {
        return date.getHour() >= BEGINNING_OF_NIGHT || date.getHour() <= ENDING_OF_NIGHT;
    }

    public boolean isSunday() {
        return date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }
}
