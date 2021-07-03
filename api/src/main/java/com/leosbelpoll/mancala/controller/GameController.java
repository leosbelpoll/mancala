package com.leosbelpoll.mancala.controller;

import com.leosbelpoll.mancala.dto.PlayRequestDto;
import com.leosbelpoll.mancala.dto.StartGameRequestDto;
import com.leosbelpoll.mancala.model.Game;
import com.leosbelpoll.mancala.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/game")
@Tag(name = "Game", description = "Endpoint to play game")
public class GameController {

    @Autowired
    private GameService gameService;

    @Operation(summary = "Create game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content( schema = @Schema(implementation = Game.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content( schema = @Schema(implementation = String.class))),
    })
     @PostMapping("")
     public ResponseEntity<Game> start(@RequestBody @Validated StartGameRequestDto dto) {
         return ResponseEntity.status(HttpStatus.CREATED).body(gameService.create(dto.getUser1(), dto.getUser2(), dto.getPitsNumber(), dto.getBallsNumber()));
     }

    @Operation(summary = "Play game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content( schema = @Schema(implementation = Game.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content( schema = @Schema(implementation = String.class))),
    })
    @PutMapping("/{id}")
    public ResponseEntity<Game> play(@Parameter(description = "Game Id", example="1", required = true) @PathVariable Long id, @RequestBody PlayRequestDto requestDto) {
        return ResponseEntity.ok(gameService.play(id, requestDto.getUserId(), requestDto.getPitPosition()));
    }

    @Operation(summary = "Get game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content( schema = @Schema(implementation = Game.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content( schema = @Schema(implementation = String.class))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Game> getGame(@Parameter(description = "Game Id", example="1", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(gameService.get(id));
    }
}
