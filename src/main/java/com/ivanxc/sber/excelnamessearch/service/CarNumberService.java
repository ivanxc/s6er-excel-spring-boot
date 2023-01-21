package com.ivanxc.sber.excelnamessearch.service;

import com.ivanxc.sber.excelnamessearch.exception.ParameterException;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CarNumberService {
    @Value("${valuesAmount}")
    private final int valuesAmount;
    @Value("${pathToExcel}")
    private final String pathToExcel;

    public Optional<Set<String>> findNames(List<String> values) {
        Optional<Set<String>> foundNames;
        try (InputStream file = ClassLoader.getSystemResourceAsStream(pathToExcel);
             Workbook workbook = new XSSFWorkbook(file)) {
            Sheet sheet = workbook.getSheetAt(0);
            foundNames = findNames(sheet, values);
        } catch (IOException e) {
            throw new UncheckedIOException("An error occurred while trying to access the resource", e);
        }
        return foundNames;
    }

    private Optional<Set<String>> findNames(Sheet sheet, List<String> values) {
        if (values.size() != valuesAmount) {
            throw new ParameterException("Given " + values.size() + " parameters but expected " + valuesAmount);
        }

        Set<String> result = new HashSet<>();

        for (Row r : sheet) {
            Cell c = r.getCell(0);
            if (c != null) {
                String value;
                if (c.getCellType() == CellType.STRING) {
                    value = c.getStringCellValue();
                    if (values.contains(value)) {
                        result.add(value);
                    }
                }
            }
        }

        return result.size() > 0 ? Optional.of(result) : Optional.empty();
    }
}
