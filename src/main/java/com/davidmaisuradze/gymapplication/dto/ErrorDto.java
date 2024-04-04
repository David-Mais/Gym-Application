package com.davidmaisuradze.gymapplication.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {
    private String errorMessage;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> details = new HashMap<>();
    private String errorCode;
}
