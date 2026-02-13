package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.powerstats.PowerStatsService;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.enums.Race;
import br.com.gubee.interview.model.request.CreateHeroRequest;
import br.com.gubee.interview.model.request.ReturnHeroWithStatsRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HeroServiceTest {

    @Mock
    private HeroRepository heroRepository;

    @Mock
    private PowerStatsService powerStatsService;

    @InjectMocks
    private HeroService heroService;

    private UUID heroId;
    private UUID powerStatsId;
    private Hero hero;
    private PowerStats powerStats;
    private CreateHeroRequest createHeroRequest;

    @BeforeEach
    void setUp() {
        heroId = UUID.randomUUID();
        powerStatsId = UUID.randomUUID();

        hero = Hero.builder()
                .id(heroId)
                .name("Superman")
                .race(Race.ALIEN)
                .powerStatsId(powerStatsId)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .enabled(true)
                .build();

        powerStats = PowerStats.builder()
                .id(powerStatsId)
                .strength(100)
                .agility(80)
                .dexterity(90)
                .intelligence(70)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        createHeroRequest = CreateHeroRequest.builder()
                .name("Superman")
                .race(Race.ALIEN)
                .strength(100)
                .agility(80)
                .dexterity(90)
                .intelligence(70)
                .build();
    }

    @Test
    void create_shouldCreateHeroAndPowerStatsSuccessfully() {
        when(powerStatsService.create(any(PowerStats.class))).thenReturn(powerStatsId);
        when(heroRepository.create(any(Hero.class))).thenReturn(heroId);

        UUID result = heroService.create(createHeroRequest);

        assertThat(result).isEqualTo(heroId);

        verify(powerStatsService).create(any(PowerStats.class));
        verify(heroRepository).create(any(Hero.class));
    }

    @Test
    void read_shouldReadHeroAndPowerStatsSuccessfully() {
        when(powerStatsService.findById(any(UUID.class))).thenReturn(powerStats);
        when(heroRepository.findById(any(UUID.class))).thenReturn(hero);

        var result = heroService.findWithStatsById(heroId);
        assertThat(result.getId()).isEqualTo(heroId);
        assertThat(result.getName()).isEqualTo(hero.getName());
        assertThat(result.getStrength()).isEqualTo(powerStats.getStrength());

        verify(heroRepository).findById(any(UUID.class));
        verify(powerStatsService).findById(any(UUID.class));
    }

    @Test
    void read_shouldSearchHeroWithStatsByName() {
        when(powerStatsService.findById(any(UUID.class))).thenReturn(powerStats);
        when(heroRepository.findByName(any(String.class))).thenReturn(hero);

        var result = heroService.searchHeroWithStatsByName("Superman");

        assertThat(result.getId()).isEqualTo(heroId);
        assertThat(result.getName()).isEqualTo(hero.getName());
        assertThat(result.getStrength()).isEqualTo(powerStats.getStrength());
        assertThat(result.getAgility()).isEqualTo(powerStats.getAgility());
        assertThat(result.getDexterity()).isEqualTo(powerStats.getDexterity());
        assertThat(result.getIntelligence()).isEqualTo(powerStats.getIntelligence());

        verify(heroRepository).findByName(any(String.class));
        verify(powerStatsService).findById(any(UUID.class));
    }

    @Test
    void patch_shouldPartiallyUpdateHero() {

        Hero modifiedHero = Hero.builder()
                .name("Robin")
                .race(Race.HUMAN).build();

        when(heroRepository.findById(any(UUID.class))).thenReturn(hero);

        heroService.update(heroId, modifiedHero);

        assertThat(hero.getName()).isEqualTo("Robin");

        verify(heroRepository).patch(hero);
    }

    @Test
    void delete_shouldDeleteHero() {
        when(heroRepository.findById(heroId)).thenReturn(hero).thenReturn(null);

        heroService.deleteById(heroId);
        ReturnHeroWithStatsRequest result = heroService.findWithStatsById(heroId);

        assertThat(result).isNull();

        verify(heroRepository).deleteById(heroId);
        verify(powerStatsService).deleteById(any(UUID.class));
    }
}
