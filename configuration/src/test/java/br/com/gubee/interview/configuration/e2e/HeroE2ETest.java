package br.com.gubee.interview.configuration.e2e;

import br.com.gubee.interview.core.domain.model.Race;
import br.com.gubee.interview.adapters.web.request.CreateHeroRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("it")
public class HeroE2ETest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("container_db")
            .withUsername("root")
            .withPassword("root");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("jdbc.url", postgres::getJdbcUrl);
        registry.add("jdbc.username", postgres::getUsername);
        registry.add("jdbc.password", postgres::getPassword);
        registry.add("jdbc.schema", () -> "interview_service");
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void createHero_shouldCreateAndReturnCreated() throws Exception {
        CreateHeroRequest heroRequest = CreateHeroRequest.builder()
                .name("Hulk")
                .race(Race.HUMAN)
                .strength(10)
                .agility(5)
                .dexterity(6)
                .intelligence(2)
                .build();

        String location = mockMvc.perform(post("/api/v1/heroes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(heroRequest)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getHeader("Location");

        assertThat(location).isNotNull();
        UUID heroId = UUID.fromString(location.substring(location.lastIndexOf('/') + 1));

        Integer heroCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM hero WHERE id = ?", Integer.class, heroId);
        assertThat(heroCount).isEqualTo(1);
    }

    @Test
    void findById_shouldReturnHero() throws Exception {
        UUID heroId = UUID.randomUUID();
        UUID powerStatsId = UUID.randomUUID();
        jdbcTemplate.update("INSERT INTO power_stats (id, strength, agility, dexterity, intelligence) VALUES (?, 10, 8, 7, 9)", powerStatsId);
        jdbcTemplate.update("INSERT INTO hero (id, name, race, power_stats_id) VALUES (?, 'Thor', 'ALIEN', ?)", heroId, powerStatsId);

        mockMvc.perform(get("/api/v1/heroes/{id}", heroId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(heroId.toString())))
                .andExpect(jsonPath("$.name", is("Thor")))
                .andExpect(jsonPath("$.race", is("ALIEN")));
    }

    @Test
    void findByName_shouldReturnHero() throws Exception {
        UUID heroId = UUID.randomUUID();
        UUID powerStatsId = UUID.randomUUID();
        jdbcTemplate.update("INSERT INTO power_stats (id, strength, agility, dexterity, intelligence) VALUES (?, 10, 8, 7, 9)", powerStatsId);
        jdbcTemplate.update("INSERT INTO hero (id, name, race, power_stats_id) VALUES (?, 'Captain America', 'HUMAN', ?)", heroId, powerStatsId);

        mockMvc.perform(get("/api/v1/heroes").param("name", "Captain America"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Captain America")))
                .andExpect(jsonPath("$.race", is("HUMAN")));
    }

    @Test
    void patchHero_shouldUpdateHeroName() throws Exception {
        UUID heroId = UUID.randomUUID();
        UUID powerStatsId = UUID.randomUUID();
        jdbcTemplate.update("INSERT INTO power_stats (id, strength, agility, dexterity, intelligence) VALUES (?, 1, 2, 3, 4)", powerStatsId);
        jdbcTemplate.update("INSERT INTO hero (id, name, race, power_stats_id) VALUES (?, 'Iron Man', 'HUMAN', ?)", heroId, powerStatsId);

        String patchBody = "{\"name\":\"Tony Stark\"}";

        mockMvc.perform(patch("/api/v1/heroes/{id}", heroId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patchBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Tony Stark")));

        String updatedName = jdbcTemplate.queryForObject("SELECT name FROM hero WHERE id = ?", String.class, heroId);
        assertThat(updatedName).isEqualTo("Tony Stark");
    }

    @Test
    void deleteHero_shouldDeleteHeroAndPowerStats() throws Exception {
        UUID heroId = UUID.randomUUID();
        UUID powerStatsId = UUID.randomUUID();
        jdbcTemplate.update("INSERT INTO power_stats (id, strength, agility, dexterity, intelligence) VALUES (?, 5, 5, 5, 5)", powerStatsId);
        jdbcTemplate.update("INSERT INTO hero (id, name, race, power_stats_id) VALUES (?, 'Black Widow', 'HUMAN', ?)", heroId, powerStatsId);

        mockMvc.perform(delete("/api/v1/heroes/{id}", heroId))
                .andExpect(status().isNoContent());

        Integer heroCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM hero WHERE id = ?", Integer.class, heroId);
        assertThat(heroCount).isZero();
        Integer statsCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM power_stats WHERE id = ?", Integer.class, powerStatsId);
        assertThat(statsCount).isZero();
    }
}
