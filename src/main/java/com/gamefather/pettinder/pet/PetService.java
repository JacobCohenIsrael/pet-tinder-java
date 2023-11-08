package com.gamefather.pettinder.pet;

import com.gamefather.pettinder.pet.models.PetDocument;
import com.gamefather.pettinder.pet.models.PetModel;
import com.gamefather.pettinder.shared.mappers.AnimalToPetMapper;
import com.gamefather.pettinder.vendors.petfinder.PetfinderService;
import com.gamefather.pettinder.vendors.petfinder.models.AnimalModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetService {

    private final PetRepository petRepository;

    private final PetfinderService petfinderService;

    @Autowired
    public PetService(
            PetRepository petRepository,
            PetfinderService petfinderService
    ) {
        this.petRepository = petRepository;
        this.petfinderService = petfinderService;
    }

    public List<PetModel> GetPetsList(String petType, String petGender, int limit) throws Exception {
        AnimalModel[] animals = this.petfinderService.GetAnimals(petType, petGender, limit);
        List<AnimalModel> filteredAnimals = Arrays.stream(animals).filter(animal -> animal.photos.length == 0).toList();
        return filteredAnimals.stream().map(AnimalToPetMapper::ConvertToPetModel).collect(Collectors.toList());
    }

    public void Vote(int petId, boolean like) {
        PetDocument pet = petRepository.findByPetId(petId);

        if (pet == null) {
            pet = PetDocument.builder().petId(petId).likes(0).dislikes(0).build();
        }


        // TODO: atomic operation instead of update

        if (like) {
            pet.likes++;
        } else {
            pet.dislikes++;
        }

        petRepository.save(pet);
    }
}
