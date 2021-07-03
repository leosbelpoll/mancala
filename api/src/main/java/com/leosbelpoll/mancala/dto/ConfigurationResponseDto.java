package com.leosbelpoll.mancala.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class ConfigurationResponseDto {
    private Integer maxPitsNumber;
    private Integer pitsNumber;
    private Integer maxBallsNumber;
    private Integer ballsNumber;
}
