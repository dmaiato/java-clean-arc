package br.com.gubee.interview.adapters.web.response;

import br.com.gubee.interview.core.domain.result.HeroWithStatsResult;
import br.com.gubee.interview.core.domain.model.Race;

import java.util.UUID;

public record HeroWithStatsResponse(
    UUID id,
    String name,
    Race race,
    boolean enabled,
    int strength,
    int agility,
    int dexterity,
    int intelligence
) {
    public static HeroWithStatsResponse from(HeroWithStatsResult result) {
        var hero = result.getHero();
        var stats = result.getPowerStats();
        return new HeroWithStatsResponse(
            hero.getId(),
            hero.getName(),
            hero.getRace(),
            hero.isEnabled(),
            stats.getStrength(),
            stats.getAgility(),
            stats.getDexterity(),
            stats.getIntelligence()
        );
    }
}
