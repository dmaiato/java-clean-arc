package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.powerstats.PowerStatsRepository;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.enums.Race;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({HeroRepository.class, PowerStatsRepository.class})
class HeroRepositoryIT {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("container_db")
            .withUsername("root")
            .withPassword("root");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private HeroRepository heroRepository;

    @Autowired
    private PowerStatsRepository powerStatsRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    private UUID powerStatsId;
    private UUID heroId;
    private Hero hero;

    @Test()
    void infra_connectionEstablished() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }

    @BeforeEach
    public void setup() {

        jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS public");

        jdbcTemplate.execute("SET search_path TO public");

        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS power_stats (" +
                        "    id           UUID PRIMARY KEY NOT NULL DEFAULT gen_random_uuid()," +
                        "    strength     SMALLINT         NOT NULL," +
                        "    agility      SMALLINT         NOT NULL," +
                        "    dexterity    SMALLINT         NOT NULL," +
                        "    intelligence SMALLINT         NOT NULL," +
                        "    created_at   TIMESTAMPTZ      NOT NULL DEFAULT now()," +
                        "    updated_at   TIMESTAMPTZ      NOT NULL DEFAULT now()" +
                        ")"
        );

        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS hero (" +
                        "    id             UUID PRIMARY KEY NOT NULL DEFAULT gen_random_uuid()," +
                        "    name           VARCHAR(255)     NOT NULL UNIQUE," +
                        "    race           VARCHAR(255)     NOT NULL," +
                        "    power_stats_id UUID             NOT NULL," +
                        "    enabled        BOOLEAN          NOT NULL DEFAULT TRUE," +
                        "    created_at     TIMESTAMPTZ      NOT NULL DEFAULT now()," +
                        "    updated_at     TIMESTAMPTZ      NOT NULL DEFAULT now()," +
                        "    CHECK ( race IN ('HUMAN', 'ALIEN', 'DIVINE', 'CYBORG'))," +
                        "    CONSTRAINT FK_power_stats FOREIGN KEY (power_stats_id) REFERENCES power_stats(id)" +
                        ")"
        );

        jdbcTemplate.execute("DELETE FROM hero");
        jdbcTemplate.execute("DELETE FROM power_stats");

        PowerStats powerStats = PowerStats.builder()
                .strength(10)
                .agility(8)
                .dexterity(9)
                .intelligence(7)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        powerStatsId = powerStatsRepository.create(powerStats);

        hero = Hero.builder()
                .name("Superman")
                .race(Race.ALIEN)
                .powerStatsId(powerStatsId)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .enabled(true)
                .build();

        heroId = heroRepository.create(hero);
    }

    @Test
    void create_shouldAddNewHero() {
        hero = Hero.builder()
                .name("Batman")
                .race(Race.ALIEN)
                .powerStatsId(powerStatsId)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .enabled(true)
                .build();

        UUID heroId = heroRepository.create(hero);

        var hero = heroRepository.findById(heroId);

        assertThat(hero).isInstanceOf(Hero.class);
        assertThat(hero.getName()).isEqualToIgnoringCase("Batman");
        assertThat(hero.getId()).isEqualTo(heroId);
    }

    @Test
    void read_shouldGetHeroById() {
        Hero hero = heroRepository.findById(heroId);
        assertThat(hero).isEqualTo(hero);
        assertThat(hero.getName()).isEqualToIgnoringCase("Superman");
        assertThat(hero.getId()).isEqualTo(heroId);
    }

    @Test
    void read_shouldGetHeroByName() {
        Hero hero1 = heroRepository.findByName("SUPER");
        Hero hero2 = heroRepository.findByName("MAN");

        assertThat(hero1).isEqualTo(hero2);
        assertThat(hero1.getName()).isEqualTo("Superman");
        assertThat(hero2.getName()).isEqualTo("Superman");
    }

    @Test
    void patch_shouldPatchHero() {
        Hero hero1 = heroRepository.findById(heroId);
        hero1.setName("Robin");
        hero1.setRace(Race.HUMAN);
        hero1.setEnabled(false);
        heroRepository.patch(hero1);

        Hero hero2 =  heroRepository.findById(heroId);
        assertThat(hero2.getName()).isEqualToIgnoringCase("Robin");
        assertThat(hero2.getRace()).isEqualTo(Race.HUMAN);
        assertThat(hero2.getEnabled()).isFalse();
    }

    @Test
    void delete_shouldDeleteHero() {
        heroRepository.deleteById(heroId);

        hero =  heroRepository.findById(heroId);
        assertThat(hero).isNull();
    }
}
