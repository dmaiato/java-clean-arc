package br.com.gubee.interview.adapters.persistence.repository;

import br.com.gubee.interview.adapters.persistence.entity.HeroEntity;
import br.com.gubee.interview.adapters.persistence.mapper.HeroPersistenceMapper;
import br.com.gubee.interview.adapters.persistence.mapper.HeroRowMapper;
import br.com.gubee.interview.core.application.port.out.DeleteHeroPort;
import br.com.gubee.interview.core.application.port.out.FindHeroPort;
import br.com.gubee.interview.core.application.port.out.SaveHeroPort;
import br.com.gubee.interview.core.application.port.out.UpdateHeroPort;
import br.com.gubee.interview.core.domain.model.Hero;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class HeroRepositoryImpl implements SaveHeroPort, FindHeroPort, UpdateHeroPort, DeleteHeroPort {

    private static final String CREATE_HERO_QUERY = "INSERT INTO hero" +
            " (id, name, race, power_stats_id, created_at, updated_at, enabled)" +
            " VALUES (:id, :name, :race, :powerStatsId, :createdAt, :updatedAt, :enabled)";

    private static final String FIND_HERO_BY_ID_QUERY = "SELECT * FROM hero WHERE id = :id";

    private final static String FIND_HERO_BY_NAME_QUERY = "SELECT * FROM hero WHERE name ~* :name";

    private final static String PATCH_HERO_FROM_ID_QUERY = "UPDATE hero SET name = :name, race = :race, enabled = :enabled, updated_at = :updated_at WHERE id = :id";

    private final static String DELETE_HERO_BY_ID_QUERY = "DELETE FROM hero WHERE id = :id";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final HeroPersistenceMapper heroPersistenceMapper;

    @Override
    public Hero save(Hero hero) {
        HeroEntity heroEntity = heroPersistenceMapper.toEntity(hero);
        final MapSqlParameterSource params = new MapSqlParameterSource("id", heroEntity.getId())
                .addValue("name", heroEntity.getName())
                .addValue("race", heroEntity.getRace().name())
                .addValue("powerStatsId", heroEntity.getPowerStatsId())
                .addValue("createdAt", Timestamp.from(heroEntity.getCreatedAt()))
                .addValue("updatedAt", Timestamp.from(heroEntity.getUpdatedAt()))
                .addValue("enabled", heroEntity.isEnabled());

        namedParameterJdbcTemplate.update(CREATE_HERO_QUERY, params);
        return hero;
    }

    @Override
    public Optional<Hero> findById(UUID id) {
        final SqlParameterSource params = new MapSqlParameterSource("id", id);
        List<HeroEntity> heroEntities = namedParameterJdbcTemplate.query(FIND_HERO_BY_ID_QUERY, params, new HeroRowMapper());
        return heroEntities.stream().findFirst().map(heroPersistenceMapper::toDomain);
    }

    @Override
    public Optional<Hero> findByName(String name) {
        final SqlParameterSource params = new MapSqlParameterSource("name", name);
        List<HeroEntity> heroEntities = namedParameterJdbcTemplate.query(FIND_HERO_BY_NAME_QUERY, params, new HeroRowMapper());
        return heroEntities.stream().findFirst().map(heroPersistenceMapper::toDomain);
    }

    @Override
    public void patch(Hero hero) {
        HeroEntity heroEntity = heroPersistenceMapper.toEntity(hero);
        final SqlParameterSource params = new MapSqlParameterSource("id", heroEntity.getId())
                .addValue("name", heroEntity.getName())
                .addValue("race", heroEntity.getRace().name())
                .addValue("enabled", heroEntity.isEnabled())
                .addValue("updated_at", Timestamp.from(heroEntity.getUpdatedAt()));

        namedParameterJdbcTemplate.update(PATCH_HERO_FROM_ID_QUERY, params);
    }

    @Override
    public void deleteById(UUID id) {
        final SqlParameterSource params = new MapSqlParameterSource("id", id);
        namedParameterJdbcTemplate.update(DELETE_HERO_BY_ID_QUERY, params);
    }
}
