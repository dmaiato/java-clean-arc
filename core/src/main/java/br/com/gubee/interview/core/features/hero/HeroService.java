package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.powerstats.PowerStatsService;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.request.CreateHeroRequest;
import br.com.gubee.interview.model.request.ReturnHeroWithStatsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HeroService {

    private final PowerStatsService powerStatsService;
    private final HeroRepository heroRepository;

    @Transactional
    public UUID create(CreateHeroRequest createHeroRequest) {
        var powerStats = new PowerStats(createHeroRequest);
        var powerStatsId = powerStatsService.create(powerStats);

        return heroRepository.create(new Hero(createHeroRequest, powerStatsId));
    }

    @Transactional(readOnly = true)
    public ReturnHeroWithStatsRequest findWithStatsById(UUID id) {
        var hero = heroRepository.findById(id);
        if (hero == null) return null;

        var stats = powerStatsService.findById(hero.getPowerStatsId());

        return new ReturnHeroWithStatsRequest(hero, stats);
    }

    @Transactional(readOnly = true)
    public ReturnHeroWithStatsRequest searchHeroWithStatsByName(String name) {
        var hero = heroRepository.findByName(name);
        if (hero == null) return null;
        var stats = powerStatsService.findById(hero.getPowerStatsId());
        return new ReturnHeroWithStatsRequest(hero, stats);
    }

    @Transactional()
    public Hero update(UUID id, Hero partialHero) {
        var hero = heroRepository.findById(id);
        if (hero == null) return null;

        if (partialHero.getName() != null) hero.setName(partialHero.getName());
        if (partialHero.getRace() != null) hero.setRace(partialHero.getRace());
        if (partialHero.getEnabled() != null) hero.setEnabled(partialHero.getEnabled());

        heroRepository.patch(hero);
        return hero;
    }

    @Transactional()
    public void deleteById(UUID id) {
        var hero = heroRepository.findById(id);
        heroRepository.deleteById(hero.getId());
        powerStatsService.deleteById(hero.getPowerStatsId());
    }
}
