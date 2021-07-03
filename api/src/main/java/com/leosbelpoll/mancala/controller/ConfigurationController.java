package com.leosbelpoll.mancala.controller;

import com.leosbelpoll.mancala.dto.ConfigurationResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/configs")
@Tag(name = "Configuration", description = "Get default configuration")
public class ConfigurationController {

    @Value(value = "${app.configs.maxPitsNumber}")
    private Integer maxPitsNumber;

    @Value("${app.configs.maxBallsNumber}")
    private Integer maxBallsNumber;

    @Value(value = "${app.configs.pitsNumber}")
    private Integer pitsNumber;

    @Value("${app.configs.ballsNumber}")
    private Integer ballsNumber;

    @GetMapping("")
    @Operation(summary = "Get configuration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content( schema = @Schema(implementation = ConfigurationResponseDto.class))),
    })
    public ResponseEntity<ConfigurationResponseDto> get() {
        ConfigurationResponseDto dto = ConfigurationResponseDto.builder()
                .maxBallsNumber(maxBallsNumber)
                .ballsNumber(ballsNumber)
                .pitsNumber(pitsNumber)
                .maxPitsNumber(maxPitsNumber)
                .build();
        return ResponseEntity.ok(dto);
    }
}
