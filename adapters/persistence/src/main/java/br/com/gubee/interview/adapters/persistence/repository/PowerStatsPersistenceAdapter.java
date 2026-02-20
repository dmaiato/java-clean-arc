package br.com.gubee.interview.adapters.persistence.repository;

import br.com.gubee.interview.adapters.persistence.entity.PowerStatsEntity;
import br.com.gubee.interview.adapters.persistence.mapper.PowerStatsPersistenceMapper;
import br.com.gubee.interview.adapters.persistence.mapper.PowerStatsRowMapper;
import br.com.gubee.interview.core.application.port.out.DeletePowerStatsPort;
import br.com.gubee.interview.core.application.port.out.FindPowerStatsPort;
import br.com.gubee.interview.core.application.port.out.SavePowerStatsPort;
import br.com.gubee.interview.core.domain.model.PowerStats;
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
public class PowerStatsPersistenceAdapter implements SavePowerStatsPort, FindPowerStatsPort, DeletePowerStatsPort {

    private static final String CREATE_POWER_STATS_QUERY = "INSERT INTO power_stats" +
            " (id, strength, agility, dexterity, intelligence, created_at, updated_at)" +
            " VALUES (:id, :strength, :agility, :dexterity, :intelligence, :createdAt, :updatedAt)";

    private static final String FIND_POWER_STATS_BY_ID_QUERY = "SELECT * FROM power_stats WHERE id = :id";

    private final static String DELETE_POWER_STATS_BY_ID_QUERY = "DELETE FROM power_stats WHERE id = :id";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final PowerStatsPersistenceMapper powerStatsPersistenceMapper;

    @Override
    public PowerStats save(PowerStats powerStats) {
        PowerStatsEntity powerStatsEntity = powerStatsPersistenceMapper.toEntity(powerStats);
        final MapSqlParameterSource params = new MapSqlParameterSource("id", powerStatsEntity.getId())
                .addValue("strength", powerStatsEntity.getStrength())
                .addValue("agility", powerStatsEntity.getAgility())
                .addValue("dexterity", powerStatsEntity.getDexterity())
                .addValue("intelligence", powerStatsEntity.getIntelligence())
                .addValue("createdAt", Timestamp.from(powerStatsEntity.getCreatedAt()))
                .addValue("updatedAt", Timestamp.from(powerStatsEntity.getUpdatedAt()));

        namedParameterJdbcTemplate.update(CREATE_POWER_STATS_QUERY, params);
        return powerStats;
    }

    @Override
    public Optional<PowerStats> findById(UUID id) {
        final SqlParameterSource params = new MapSqlParameterSource("id", id);
        List<PowerStatsEntity> powerStatsEntities = namedParameterJdbcTemplate.query(FIND_POWER_STATS_BY_ID_QUERY, params, new PowerStatsRowMapper());
        return powerStatsEntities.stream().findFirst().map(powerStatsPersistenceMapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        final SqlParameterSource params = new MapSqlParameterSource("id", id);
        namedParameterJdbcTemplate.update(DELETE_POWER_STATS_BY_ID_QUERY, params);
    }
}
