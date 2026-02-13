package br.com.gubee.interview.core.features.powerstats;

import br.com.gubee.interview.core.features.utils.PowerStatsRowMapper;
import br.com.gubee.interview.model.PowerStats;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PowerStatsRepository {

    private static final String CREATE_POWER_STATS_QUERY = "INSERT INTO power_stats" +
            " (strength, agility, dexterity, intelligence)" +
            " VALUES (:strength, :agility, :dexterity, :intelligence) RETURNING id";

    private static final String FIND_POWER_STATS_QUERY = "SELECT * FROM power_stats WHERE id = :id";

    private static final String DELETE_POWER_STATS_QUERY = "DELETE FROM power_stats WHERE id = :id";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UUID create(PowerStats powerStats) {
        return namedParameterJdbcTemplate.queryForObject(
                CREATE_POWER_STATS_QUERY,
                new BeanPropertySqlParameterSource(powerStats),
                UUID.class);
    }

    PowerStats findById(UUID id) {
        final SqlParameterSource params = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.queryForObject(FIND_POWER_STATS_QUERY, params, new PowerStatsRowMapper());
    }

    void deleteById(UUID id) {
        final SqlParameterSource params = new MapSqlParameterSource("id", id);
        namedParameterJdbcTemplate.update(DELETE_POWER_STATS_QUERY, params);
    }
}
