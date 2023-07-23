package turma12.branas.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import turma12.branas.exceptions.InvalidDateException;
import turma12.branas.exceptions.InvalidDistanceException;
import turma12.branas.models.Input;
import turma12.branas.models.Movement;
import turma12.branas.models.Ride;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
public class CalcController {
    @PostMapping(value = "/calculate-price")
    public ResponseEntity<?> calculatePrice(@RequestBody Input request) {
        try {
            var movements = request.movements();
            var segments = movements.stream().map(Movement::toSegment).toList();
            return ok(Ride.getPrice(segments));
        } catch (InvalidDistanceException | InvalidDateException e) {
            return badRequest().body(e.getMessage());
        }
    }
}
