package br.com.gubee.interview.adapters.persistence.entity;

import br.com.gubee.interview.core.domain.model.Race;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class HeroEntity {
    private UUID id;
    private String name;
    private Race race;
    private UUID powerStatsId;
    private boolean enabled;
    private Instant createdAt;
    private Instant updatedAt;
}
