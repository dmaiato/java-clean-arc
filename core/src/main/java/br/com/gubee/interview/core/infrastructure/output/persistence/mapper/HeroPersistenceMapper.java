package br.com.gubee.interview.core.infrastructure.output.persistence.mapper;

import br.com.gubee.interview.core.infrastructure.output.persistence.entity.HeroEntity;
import br.com.gubee.interview.domain.model.Hero;
import org.springframework.stereotype.Component;

@Component
public class HeroPersistenceMapper {

    public Hero toDomain(HeroEntity entity) {
        // This is tricky because the domain object's constructor is private
        // and it expects to manage its own state. A proper CQS/DDD approach
        // would involve re-hydrating the object without calling business logic.
        // For this, we need a constructor or factory in Hero for re-hydration.
        // Let's assume we add a dedicated constructor for this.
        return new Hero(entity.getId(), entity.getName(), entity.getRace(),
                entity.getPowerStatsId(), entity.getCreatedAt(),
                entity.getUpdatedAt(), entity.isEnabled());
    }

    public HeroEntity toEntity(Hero hero) {
        HeroEntity entity = new HeroEntity();
        entity.setId(hero.getId());
        entity.setName(hero.getName());
        entity.setRace(hero.getRace());
        entity.setPowerStatsId(hero.getPowerStatsId());
        entity.setCreatedAt(hero.getCreatedAt());
        entity.setUpdatedAt(hero.getUpdatedAt());
        entity.setEnabled(hero.isEnabled());
        return entity;
    }
}
