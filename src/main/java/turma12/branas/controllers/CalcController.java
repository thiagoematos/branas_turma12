package turma12.branas.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

class Mov {
    public String ds;
    public Integer dist;
}

class Req {
    public Mov[] body;
}

@Slf4j
@RestController
public class CalcController {
    @PostMapping(value = "/calc")
    public ResponseEntity<?> calc(@RequestBody Req req) {
        var result = 0.0d;
        for (var mov : req.body) {

            var ds = new Date(mov.ds);

            if (mov.dist != null && mov.dist > 0) {

                if (mov.ds != null) {

                    // overnight
                    if (ds.getHours() >= 22 || ds.getHours() <= 6) {

                        // not sunday
                        if (ds.getDay() != 0) {

                            result += mov.dist * 3.90;
                            // sunday
                        } else {
                            result += mov.dist * 5;

                        }

                    } else {
                        // sunday
                        if (ds.getDay() == 0) {

                            result += mov.dist * 2.9;
                        } else {
                            result += mov.dist * 2.10;
                        }

                    }

                } else {
                    // System.out.println(d);
                    return ResponseEntity.ok(-2);
                }

            } else {

                // System.out.println(req.body.dist);
                return ResponseEntity.ok(-1);
            }
        }
        if (result < 10) {
            result = 10;
        }
        return ResponseEntity.ok(result);
    }
}
