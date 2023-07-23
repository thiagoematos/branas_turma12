package turma12.branas.models;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@RequiredArgsConstructor
public class Ride {
    private static final int MINIMUM_PRICE = 10;

    public static double getPrice(List<Segment> segments) {
        var totalPrice = segments.stream()
                .mapToDouble(Fare::calculate)
                .sum();
        return totalPrice < MINIMUM_PRICE
                ? MINIMUM_PRICE
                : totalPrice;
    }
}
