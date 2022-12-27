package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import guru.springframework.sfgpetclinic.services.map.PetMapService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    @Mock
    VisitService visitService;

    @Spy //@Mock
    PetMapService petService;

    @InjectMocks
    VisitController visitController;

    @Test
    void loadPetWithVisit() {
        // Given
        Map<String, Object> model = new HashMap<>();
        Pet pet = new Pet(12L);
        Pet anotherPet = new Pet(3L);
        petService.save(pet);
        petService.save(anotherPet);

        given(petService.findById(anyLong())).willCallRealMethod();

        // When
        Visit visit = visitController.loadPetWithVisit(12L, model);

        // Then
        assertThat(visit).isNotNull();
        assertThat(visit.getPet()).isNotNull();
        assertThat(visit.getPet().getId()).isEqualTo(12L);
        verify(petService, times(1)).findById(anyLong());
    }

    @Test
    void loadPetWithVisitWithStubbing() {
        // Given
        Map<String, Object> model = new HashMap<>();
        Pet pet = new Pet(12L);
        Pet anotherPet = new Pet(3L);
        petService.save(pet);
        petService.save(anotherPet);

        given(petService.findById(anyLong())).willReturn(anotherPet);

        // When
        Visit visit = visitController.loadPetWithVisit(3L, model);

        // Then
        assertThat(visit).isNotNull();
        assertThat(visit.getPet()).isNotNull();
        assertThat(visit.getPet().getId()).isEqualTo(anotherPet.getId());
        verify(petService, times(1)).findById(anyLong());
    }
}