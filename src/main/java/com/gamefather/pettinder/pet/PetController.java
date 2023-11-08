package com.gamefather.pettinder.pet;

import com.gamefather.pettinder.pet.models.PetModel;
import com.gamefather.pettinder.pet.models.VotePetRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("pet")
@Validated
@Slf4j
@Tag(name = "Pet Controller")
public class PetController {

    private final PetService petService;

    @Autowired
    public PetController(
            PetService petService
    ) {
        this.petService = petService;
    }

    @GetMapping("list")
    @Operation(summary = "Get pets list")
    public List<PetModel> GetPetsList(@RequestParam String petType, @RequestParam String petGender, @RequestParam(required = false, defaultValue = "10") int limit) throws Exception {
        return this.petService.GetPetsList(petType, petGender, limit);
    }

    @PostMapping("vote")
    @Operation(summary = "Like or Dislike a specific pet")
    public void Vote(@RequestBody VotePetRequest votePetRequest) {
        petService.Vote(votePetRequest.getPetId(), votePetRequest.isLike());
    }
}
