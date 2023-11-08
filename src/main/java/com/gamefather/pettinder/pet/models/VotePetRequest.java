package com.gamefather.pettinder.pet.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class VotePetRequest {

    @NotNull(message = "petId cannot be empty")
    @Schema(description = "Pet ID by Petfinder")
    private int petId;

    @NotNull(message = "like must be a valid boolean")
    @Schema(description = "True for like, false for dislike")
    private boolean like;
}
