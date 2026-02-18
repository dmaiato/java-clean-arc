package br.com.gubee.interview.domain.model;

import lombok.*;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

@Getter
@ToString
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = PUBLIC)
@NoArgsConstructor(access = PRIVATE)
public class Hero {

    private UUID id;
    private String name;
    private Race race;
    private UUID powerStatsId;
    private Instant createdAt;
    private Instant updatedAt;
    private boolean enabled;

    public static Hero create(String name, Race race, UUID powerStatsId) {
        Objects.requireNonNull(name, "'name' cannot be null");
        Objects.requireNonNull(race, "'race' cannot be null");
        Objects.requireNonNull(powerStatsId, "'powerStatsId' cannot be null");

        var hero = new Hero();
        hero.id = UUID.randomUUID();
        hero.name = name;
        hero.race = race;
        hero.powerStatsId = powerStatsId;
        hero.createdAt = Instant.now();
        hero.updatedAt = Instant.now();
        hero.enabled = true;
        return hero;
    }

    public void updateDetails(String name, Race race) {
        Objects.requireNonNull(name, "'name' cannot be null");
        Objects.requireNonNull(race, "'race' cannot be null");
        this.name = name;
        this.race = race;
        this.updatedAt = Instant.now();
    }
    
    public void disable() {
        this.enabled = false;
        this.updatedAt = Instant.now();
    }
    
    public void enable() {
        this.enabled = true;
        this.updatedAt = Instant.now();
    }
}
