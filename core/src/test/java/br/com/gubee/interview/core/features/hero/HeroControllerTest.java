package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.domain.model.Race;
import br.com.gubee.interview.core.infrastructure.input.web.request.CreateHeroRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import br.com.gubee.interview.domain.model.Hero;

import br.com.gubee.interview.domain.model.PowerStats;

import br.com.gubee.interview.core.infrastructure.input.web.dto.ReturnHeroWithStatsRequest;


import java.util.UUID;


import static org.hamcrest.CoreMatchers.is;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.eq;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(HeroController.class)
class HeroControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;


    @MockBean
    private HeroService heroService;


    @BeforeEach
    public void initTest() {

        when(heroService.create(any())).thenReturn(UUID.randomUUID());

    }


    @Test
    void create_AHeroWithAllRequiredArguments() throws Exception {

        //given

        // Convert the hero request into a string JSON format stub.

        final String body = objectMapper.writeValueAsString(createHeroRequest());


        //when

        final ResultActions resultActions = mockMvc.perform(post("/api/v1/heroes")

                .contentType(MediaType.APPLICATION_JSON)

                .content(body));


        //then

        resultActions.andExpect(status().isCreated()).andExpect(header().exists("Location"));

        verify(heroService, times(1)).create(any());

    }


    @Test
    void create_shouldThrowBadRequestException() throws Exception {

        final String body = objectMapper.writeValueAsString(createEmptyHeroRequest());


        final ResultActions resultActions = mockMvc.perform(post("/api/v1/heroes")

                .contentType(MediaType.APPLICATION_JSON)

                .content(body));


        resultActions.andExpect(status().isBadRequest());

        verify(heroService, never()).create(any());

    }


    @Test
    void findById_shouldReturnHero_whenHeroExists() throws Exception {

        UUID heroId = UUID.randomUUID();

        ReturnHeroWithStatsRequest heroDto = new ReturnHeroWithStatsRequest(

                Hero.builder().id(heroId).name("Superman").race(Race.ALIEN).enabled(true).build(),

                PowerStats.builder().strength(10).agility(10).dexterity(10).intelligence(10).build()

        );

        when(heroService.findWithStatsById(heroId)).thenReturn(heroDto);


        mockMvc.perform(get("/api/v1/heroes/{id}", heroId))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.id", is(heroId.toString())))

                .andExpect(jsonPath("$.name", is("Superman")));

    }


    @Test
    void findById_shouldReturnNotFound_whenHeroDoesNotExist() throws Exception {

        UUID heroId = UUID.randomUUID();

        when(heroService.findWithStatsById(heroId)).thenReturn(null);


        mockMvc.perform(get("/api/v1/heroes/{id}", heroId))

                .andExpect(status().isNotFound());

    }


    @Test
    void findByName_shouldReturnHero_whenHeroExists() throws Exception {

        String heroName = "Superman";

        ReturnHeroWithStatsRequest heroDto = new ReturnHeroWithStatsRequest(

                Hero.builder().id(UUID.randomUUID()).name(heroName).race(Race.ALIEN).enabled(true).build(),

                PowerStats.builder().strength(10).agility(10).dexterity(10).intelligence(10).build()

        );

        when(heroService.searchHeroWithStatsByName(heroName)).thenReturn(heroDto);


        mockMvc.perform(get("/api/v1/heroes").param("name", heroName))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.name", is(heroName)));

    }


    @Test
    void findByName_shouldReturnOk_whenHeroDoesNotExist() throws Exception {

        String heroName = "NonExistent";

        when(heroService.searchHeroWithStatsByName(heroName)).thenReturn(null);


        mockMvc.perform(get("/api/v1/heroes").param("name", heroName))

                .andExpect(status().isOk())

                .andExpect(content().string(""));

    }


    @Test
    void patchHero_shouldUpdateHero_whenHeroExists() throws Exception {

        UUID heroId = UUID.randomUUID();

        Hero partialHero = Hero.builder().name("Clark Kent").build();

        Hero updatedHero = Hero.builder().id(heroId).name("Clark Kent").race(Race.ALIEN).build();


        when(heroService.update(eq(heroId), any(Hero.class))).thenReturn(updatedHero);


        mockMvc.perform(patch("/api/v1/heroes/{id}", heroId)

                        .contentType(MediaType.APPLICATION_JSON)

                        .content(objectMapper.writeValueAsString(partialHero)))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.name", is("Clark Kent")));

    }


    @Test
    void patchHero_shouldReturnOk_whenHeroDoesNotExist() throws Exception {

        UUID heroId = UUID.randomUUID();

        Hero partialHero = Hero.builder().name("Clark Kent").build();

        when(heroService.update(eq(heroId), any(Hero.class))).thenReturn(null);


        mockMvc.perform(patch("/api/v1/heroes/{id}", heroId)

                        .contentType(MediaType.APPLICATION_JSON)

                        .content(objectMapper.writeValueAsString(partialHero)))

                .andExpect(status().isOk())

                .andExpect(content().string(""));

    }


    @Test
    void deleteHero_shouldDeleteHero_andReturnNoContent() throws Exception {

        UUID heroId = UUID.randomUUID();

        doNothing().when(heroService).deleteById(heroId);


        mockMvc.perform(delete("/api/v1/heroes/{id}", heroId))

                .andExpect(status().isNoContent());


        verify(heroService, times(1)).deleteById(heroId);

    }


    private CreateHeroRequest createHeroRequest() {

        return CreateHeroRequest.builder()

                .name("Batman")

                .agility(5)

                .dexterity(8)

                .strength(6)

                .intelligence(10)

                .race(Race.HUMAN)

                .build();

    }


    private CreateHeroRequest createEmptyHeroRequest() {

        return CreateHeroRequest.builder().build();

    }

}
