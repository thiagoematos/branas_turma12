package turma12.branas.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class CalcControllerTest {
    private MockMvc mvc;
    @InjectMocks
    private CalcController calcController;

    @BeforeEach
    public void setup() {
        mvc = standaloneSetup(calcController).alwaysDo(print()).build();
    }

    @ParameterizedTest(name = "Dado data=''{0}'' e distância=''{1}'', então Preço=''{2}''")
    @CsvSource({
            "09 Jul 2023 23:00:00,10,50.0",
            "08 Jul 2023 23:00:00,10,39.0",
            "09 Jul 2023 21:00:00,10,29.0",
            "08 Jul 2023 21:00:00,10,21.0",
            "08 Jul 2023 21:00:00, 2,10.0"
    })
    @DisplayName("Cenário A: Deve fazer o cálculo do preço de uma corrida durante o dia")
    void sceneA(String ds, Integer dist, String result) throws Exception {
        var response = mvc.perform(
                        post("/calculate-price")
                                .contentType(APPLICATION_JSON)
                                .accept(APPLICATION_JSON)
                                .content("""
                                        {
                                          "movements": [
                                            {
                                              "date": "%s",
                                              "distance": %d
                                            }
                                          ]
                                        }
                                        """.formatted(ds, dist))
                )
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(response).isEqualTo(result);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    @NullSource
    @DisplayName("Cenário B: Deve testar distância inválida")
    void sceneB(Integer distance) throws Exception {
        mvc.perform(
                        post("/calculate-price")
                                .contentType(APPLICATION_JSON)
                                .accept(APPLICATION_JSON)
                                .content("""
                                        {
                                          "movements": [
                                            {
                                              "date": "08 Jul 2023 21:00:00",
                                              "distance": %d
                                            }
                                          ]
                                        }
                                        """.formatted(distance))
                )
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = "08 Jul 2023 21;00;00")
    @NullSource
    @EmptySource
    @DisplayName("Cenário C: Deve testar data inválida")
    void sceneC(String date) throws Exception {
        mvc.perform(
                        post("/calculate-price")
                                .contentType(APPLICATION_JSON)
                                .accept(APPLICATION_JSON)
                                .content("""
                                        {
                                          "movements": [
                                            {
                                              "date": "%s",
                                              "distance": 10
                                            }
                                          ]
                                        }
                                        """.formatted(date))
                )
                .andExpect(status().isBadRequest());
    }
}
