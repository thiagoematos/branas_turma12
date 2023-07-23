package turma12.branas.models;

import java.util.List;
import java.util.function.Predicate;

public record Fare(Predicate<Segment> condition, double value) {
    private static final List<Fare> POSSIBLE_FARES = List.of(
            new Fare(segment -> segment.isOvernight() && segment.isSunday(), 5.0),
            new Fare(segment -> segment.isOvernight() && !segment.isSunday(), 3.9),
            new Fare(segment -> !segment.isOvernight() && segment.isSunday(), 2.9),
            new Fare(segment -> !segment.isOvernight() && !segment.isSunday(), 2.1)
    );

    public static double calculate(Segment segment) {
        return POSSIBLE_FARES.stream()
                .filter(fare -> fare.condition().test(segment))
                .findFirst()
                .map(fare -> fare.value() * segment.getDistance())
                .orElse(0d);
    }
}
