package br.com.gubee.interview.core.infrastructure.output.persistence.entity;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class PowerStatsEntity {
    private UUID id;
    private int strength;
    private int agility;
    private int dexterity;
    private int intelligence;
    private Instant createdAt;
    private Instant updatedAt;
}
