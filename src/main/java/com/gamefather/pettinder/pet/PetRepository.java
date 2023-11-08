package com.gamefather.pettinder.pet;

import com.gamefather.pettinder.pet.models.PetDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface PetRepository extends MongoRepository<PetDocument, String> {
    @Query("{ 'petId' : ?0 }")
    PetDocument findByPetId(int petId);
}
