package turma12.branas.models;

import static turma12.branas.models.Segment.makeSegment;

public record Movement(
        String date,
        Integer distance) {
    public static Segment toSegment(Movement movement) {
        return makeSegment(movement.date(), movement.distance());
    }
}
