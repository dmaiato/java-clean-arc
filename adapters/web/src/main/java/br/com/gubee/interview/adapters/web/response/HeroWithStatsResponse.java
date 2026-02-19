package br.com.gubee.interview.adapters.web.response;

import br.com.gubee.interview.core.domain.model.Hero;
import br.com.gubee.interview.core.domain.model.PowerStats;
import br.com.gubee.interview.core.domain.result.HeroWithStatsResult;
import br.com.gubee.interview.core.domain.model.Race;
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
        this.enabled = hero.isEnabled();
        this.strength = stats.getStrength();
        this.agility = stats.getAgility();
        this.dexterity = stats.getDexterity();
        this.intelligence = stats.getIntelligence();
    }

    public static HeroWithStatsResponse from(HeroWithStatsResult result) {
        return new HeroWithStatsResponse(result.getHero(), result.getPowerStats());
    }
}
