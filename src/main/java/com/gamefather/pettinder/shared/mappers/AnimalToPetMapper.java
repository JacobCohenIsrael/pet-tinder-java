package com.gamefather.pettinder.shared.mappers;

import com.gamefather.pettinder.pet.models.PetModel;
import com.gamefather.pettinder.vendors.petfinder.models.AnimalModel;

public class AnimalToPetMapper {
    public static PetModel ConvertToPetModel(AnimalModel animalModel) {
        return PetModel.builder()
                .petId(animalModel.id)
                .name(animalModel.name)
                .photo(animalModel.photos[0].large)
                .build();
    }
}
