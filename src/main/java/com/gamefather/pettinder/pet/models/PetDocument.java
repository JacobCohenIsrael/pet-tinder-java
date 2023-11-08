package com.gamefather.pettinder.pet.models;

import lombok.Builder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pets")
@Builder
public class PetDocument {

    @Id
    public ObjectId _id;

    public int petId;

    public int likes;

    public int dislikes;
}
