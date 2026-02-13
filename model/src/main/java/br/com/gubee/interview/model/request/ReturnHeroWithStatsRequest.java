package br.com.gubee.interview.model.request;

import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.enums.Race;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReturnHeroWithStatsRequest {

    public UUID id;
    public String name;
    public Race race;
    private boolean enabled;
    private int strength;
    private int agility;
    private int dexterity;
    private int intelligence;

    public ReturnHeroWithStatsRequest(Hero hero, PowerStats stats) {
        this.id = hero.getId();
        this.name = hero.getName();
        this.race = hero.getRace();
        this.enabled = hero.getEnabled();
        this.strength = stats.getStrength();
        this.agility = stats.getAgility();
        this.dexterity = stats.getDexterity();
        this.intelligence = stats.getIntelligence();
    }
}
