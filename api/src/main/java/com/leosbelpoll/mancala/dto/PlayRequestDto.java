package com.leosbelpoll.mancala.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class PlayRequestDto {

    @NotNull @NotBlank
    private Long userId;

    @NotNull @NotBlank @Email
    private Integer pitPosition;
}
