package guru.springframework.services.springdatajpa;

import guru.springframework.model.Owner;
import guru.springframework.repositories.OwnerRepository;
import guru.springframework.repositories.PetRepository;
import guru.springframework.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {
    public static final String LAST_NAME = "Smith";
    @Mock
    OwnerRepository ownerRepository;
    @Mock
    PetRepository petRepository;
    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    OwnerSDJpaService service;

    Owner owner;
    Owner owner1;

    @BeforeEach
    void setUp() {
        owner = new Owner();
        owner.setId(1L);
        owner.setLastName(LAST_NAME);

        owner1 = new Owner();
        owner1.setId(2L);
    }

    @Test
    void findAll() {
        Set<Owner> returnOwnersSet = new HashSet<>();
        returnOwnersSet.add(owner);
        returnOwnersSet.add(owner1);

        when(ownerRepository.findAll()).thenReturn(returnOwnersSet);

        Set<Owner> owners = service.findAll();

        assertNotNull(owners);
        assertEquals(2, owners.size());

    }

    @Test
    void findById() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(owner));

        Owner smith = service.findById(1L);

        assertNotNull(smith);

        assertEquals(1L, smith.getId());
    }

    @Test
    void findByIdNotFound() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());

        Owner smith = service.findById(1L);

        assertNull(smith);

    }

    @Test
    void save() {
        when(ownerRepository.save(any())).thenReturn(owner);

        Owner savedOwner = service.save(owner);

        assertNotNull(savedOwner);

        verify(ownerRepository).save(any());
    }

    @Test
    void delete() {
        service.delete(owner);

        verify(ownerRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        service.deleteById(1L);

        verify(ownerRepository).deleteById(anyLong());
    }

    @Test
    void findByLastName() {
        when(ownerRepository.findByLastName(any())).thenReturn(owner);

        Owner smith = service.findByLastName(LAST_NAME);

        assertEquals(LAST_NAME, smith.getLastName());

        verify(ownerRepository).findByLastName(any());
    }
}