package com.davidmaisuradze.gymapplication.controller;

import com.davidmaisuradze.gymapplication.dto.trainingtype.TrainingTypeDto;
import com.davidmaisuradze.gymapplication.service.TrainingTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trainingtypes")
@RequiredArgsConstructor
@Tag(name = "TrainingTypeController", description = "Endpoint for managing Training Types")
public class TrainingTypeController {
    private final TrainingTypeService trainingTypeService;

    @GetMapping("/all")
    @Operation(
            description = "Get all training types that exist in the application.",
            summary = "Get all types"
    )
    @ApiResponse(
            responseCode = "200",
            description = "**All training types found**",
            content = @Content(
                    mediaType = "json",
                    schema = @Schema(implementation = TrainingTypeDto.class),
                    examples = @ExampleObject(
                            name = "All training types",
                            description = "Example of all training types that can exist in application.",
                            value = """
                                    [
                                      {
                                        "id": 1,
                                        "trainingTypeName": "box"
                                      },
                                      {
                                        "id": 2,
                                        "trainingTypeName": "dance"
                                      },
                                      {
                                        "id": 3,
                                        "trainingTypeName": "mma"
                                      }
                                    ]
                                    """
                    )
            )
    )
    public ResponseEntity<List<TrainingTypeDto>> getAll() {
        return ResponseEntity.ok(trainingTypeService.findAll());
    }
}
