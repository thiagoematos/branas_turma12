package turma12.branas.controllers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Locale.ENGLISH;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateFormatter {

    public static Optional<LocalDateTime> format(String dateAsString) {
        try {
            return Optional.of(
                    parse(
                            dateAsString,
                            ofPattern("dd MMM yyyy HH:mm:ss", ENGLISH)
                    )
            );
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
