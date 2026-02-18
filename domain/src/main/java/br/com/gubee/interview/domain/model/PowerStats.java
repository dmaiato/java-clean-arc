package br.com.gubee.interview.domain.model;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

@Getter
@ToString
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = PUBLIC)
@NoArgsConstructor(access = PRIVATE)
public class PowerStats {

    private UUID id;
    private int strength;
    private int agility;
    private int dexterity;
    private int intelligence;
    private Instant createdAt;
    private Instant updatedAt;

    public static PowerStats create(int strength, int agility, int dexterity, int intelligence) {
        if (strength < 0 || agility < 0 || dexterity < 0 || intelligence < 0) {
            throw new IllegalArgumentException("Stats cannot be negative");
        }

        var stats = new PowerStats();
        stats.id = UUID.randomUUID();
        stats.strength = strength;
        stats.agility = agility;
        stats.dexterity = dexterity;
        stats.intelligence = intelligence;
        stats.createdAt = Instant.now();
        stats.updatedAt = Instant.now();
        return stats;
    }
}
