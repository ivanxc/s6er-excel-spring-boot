package com.ivanxc.sber.excelnamessearch.conroller;

import com.ivanxc.sber.excelnamessearch.exception.ParameterException;
import com.ivanxc.sber.excelnamessearch.service.CarNumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/excel/cars")
@RequiredArgsConstructor
public class CarNumberController {
    private final CarNumberService carNumberService;

    @GetMapping("/is-present")
    public ResponseEntity<Set<String>> findNames(@RequestParam List<String> values) {
        values.forEach(v -> {
            if (!v.matches("[АВЕКМНОРСТУХ]\\d{3}(?<!000)[АВЕКМНОРСТУХ]{2}\\d{2,3}")) {
                throw new ParameterException("The passed parameter does not match the pattern.");
            }
        });

        Optional<Set<String>> foundNames = carNumberService.findNames(values);
        if (foundNames.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(foundNames.get(), HttpStatus.OK);
        }
    }
}
