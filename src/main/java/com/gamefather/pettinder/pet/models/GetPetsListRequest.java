package com.gamefather.pettinder.pet.models;

import com.gamefather.pettinder.shared.models.PetGender;
import com.gamefather.pettinder.shared.models.PetType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class GetPetsListRequest {

    @Schema( description = "The type of the pet", allowableValues = {PetType.Dog})
    public String petType;

    @Schema(description = "The gender of the pet", allowableValues = {PetGender.Male, PetGender.Female, PetGender.All})
    public String petGender;

}
