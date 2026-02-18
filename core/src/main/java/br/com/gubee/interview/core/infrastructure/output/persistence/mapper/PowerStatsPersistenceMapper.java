package br.com.gubee.interview.core.infrastructure.output.persistence.mapper;

import br.com.gubee.interview.core.infrastructure.output.persistence.entity.PowerStatsEntity;
import br.com.gubee.interview.domain.model.PowerStats;
import org.springframework.stereotype.Component;

@Component
public class PowerStatsPersistenceMapper {

    public PowerStats toDomain(PowerStatsEntity entity) {
        // Similar to Hero, we need a way to re-hydrate the PowerStats domain object.
        return new PowerStats(entity.getId(), entity.getStrength(), entity.getAgility(),
                entity.getDexterity(), entity.getIntelligence(),
                entity.getCreatedAt(), entity.getUpdatedAt());
    }

    public PowerStatsEntity toEntity(PowerStats powerStats) {
        PowerStatsEntity entity = new PowerStatsEntity();
        entity.setId(powerStats.getId());
        entity.setStrength(powerStats.getStrength());
        entity.setAgility(powerStats.getAgility());
        entity.setDexterity(powerStats.getDexterity());
        entity.setIntelligence(powerStats.getIntelligence());
        entity.setCreatedAt(powerStats.getCreatedAt());
        entity.setUpdatedAt(powerStats.getUpdatedAt());
        return entity;
    }
}
