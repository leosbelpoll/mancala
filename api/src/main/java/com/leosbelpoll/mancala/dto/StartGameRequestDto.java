package com.leosbelpoll.mancala.dto;

import com.leosbelpoll.mancala.dto.constraints.RangeConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class StartGameRequestDto {

    @NotNull @NotBlank @Email
    private String user1;

    @NotNull @NotBlank @Email
    private String user2;

    @RangeConstraint(max = "app.configs.maxPitsNumber")
    private Integer pitsNumber;

    @RangeConstraint(max = "app.configs.maxBallsNumber")
    private Integer ballsNumber;
}
