package br.com.gubee.interview.core.infrastructure.output.persistence.entity;

import br.com.gubee.interview.domain.model.Race;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class HeroEntity {
    private UUID id;
    private String name;
    private Race race;
    private UUID powerStatsId;
    private Instant createdAt;
    private Instant updatedAt;
    private boolean enabled;
}
