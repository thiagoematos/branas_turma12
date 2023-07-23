package turma12.branas.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

class DateFormatterTest {

    @Test
    @DisplayName("Cenário A: Deve testar data válida")
    void sceneA() {
        // given
        var dataValida = "01 Mar 2021 10:00:00";

        // when
        var dataFormatada = DateFormatter.format(dataValida);

        // then
        assertThat(dataFormatada).isNotEmpty();
        assertThat(dataFormatada.get())
                .hasYear(2021)
                .hasMonth(Month.MARCH)
                .hasDayOfMonth(1)
                .hasHour(10);
    }

    @Test
    @DisplayName("Cenário B: Deve testar data inválida")
    void sceneB() {
        // given
        var dataValida = "01 Mar 2021 10;00;00";

        // when
        var dataFormatada = DateFormatter.format(dataValida);

        // then
        assertThat(dataFormatada).isEmpty();
    }
}
