package one.digitalinnovation.beerstock.service;

import one.digitalinnovation.beerstock.dto.BeerDTO;
import one.digitalinnovation.beerstock.entity.Beer;
import one.digitalinnovation.beerstock.mapper.BeerMapper;
import one.digitalinnovation.beerstock.repository.BeerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BeerServiceTest {

    @Mock
    private BeerRepository beerRepository;

    @Mock
    private BeerMapper beerMapper;

    @InjectMocks
    private BeerService beerService;

    private BeerDTO beerDTO;
    private Beer beer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        beerDTO = BeerDTO.builder()
                .id(1L)
                .name("Skol")
                .brand("Ambev")
                .max(50)
                .quantity(10)
                .build();

        beer = Beer.builder()
                .id(1L)
                .name("Skol")
                .brand("Ambev")
                .max(50)
                .quantity(10)
                .build();
    }

    @Test
    void whenBeerIsCreatedThenItShouldBeReturned() {
        when(beerMapper.toModel(beerDTO)).thenReturn(beer);
        when(beerRepository.save(beer)).thenReturn(beer);
        when(beerMapper.toDTO(beer)).thenReturn(beerDTO);

        BeerDTO createdBeer = beerService.createBeer(beerDTO);

        assertEquals(beerDTO.getName(), createdBeer.getName());
        assertEquals(beerDTO.getQuantity(), createdBeer.getQuantity());
    }

    @Test
    void whenBeerIsFoundByNameThenItShouldBeReturned() {
        when(beerRepository.findByName("Skol")).thenReturn(Optional.of(beer));
        when(beerMapper.toDTO(beer)).thenReturn(beerDTO);

        BeerDTO foundBeer = beerService.findByName("Skol");

        assertEquals("Skol", foundBeer.getName());
        assertEquals("Ambev", foundBeer.getBrand());
    }
}