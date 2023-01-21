package com.ivanxc.sber.excelnamessearch.dto;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse {
    private final String message;
    private final List<String> details;
}