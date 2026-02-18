package br.com.gubee.interview.core.infrastructure.input.web.response;

import br.com.gubee.interview.domain.model.Hero;
import br.com.gubee.interview.domain.model.PowerStats;
import br.com.gubee.interview.domain.result.HeroWithStatsResult;
import br.com.gubee.interview.domain.model.Race;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HeroWithStatsResponse {

    public UUID id;
    public String name;
    public Race race;
    private boolean enabled;
    private int strength;
    private int agility;
    private int dexterity;
    private int intelligence;

    public HeroWithStatsResponse(Hero hero, PowerStats stats) {
        this.id = hero.getId();
        this.name = hero.getName();
        this.race = hero.getRace();
        this.enabled = hero.getEnabled();
        this.strength = stats.getStrength();
        this.agility = stats.getAgility();
        this.dexterity = stats.getDexterity();
        this.intelligence = stats.getIntelligence();
    }

    public static HeroWithStatsResponse from(HeroWithStatsResult result) {
        return new HeroWithStatsResponse(result.getHero(), result.getPowerStats());
    }
}
