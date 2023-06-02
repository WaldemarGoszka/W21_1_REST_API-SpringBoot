package pl.zajavka.infrastructure.database.petstore;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PetMapper {
    public Pet map(pl.zajavka.springrest.infrastructure.petstore.model.Pet pet) {
        return Pet.builder()
                .id(pet.getId())
                .name(pet.getName())
                .category(Optional.ofNullable(pet.getCategory()).map(cat -> cat.getName()).orElse(null))
                .build();
    }
}
