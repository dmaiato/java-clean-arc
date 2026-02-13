package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.utils.HeroRowMapper;
import br.com.gubee.interview.model.Hero;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class HeroRepository {

    private static final String CREATE_HERO_QUERY = "INSERT INTO hero" +
            " (name, race, power_stats_id)" +
            " VALUES (:name, :race, :powerStatsId) RETURNING id";

    private static final String FIND_HERO_BY_ID_QUERY = "SELECT * FROM hero WHERE id = :id";

    private final static String FIND_HERO_BY_NAME_QUERY = "SELECT * FROM hero WHERE name ~* :name";

    private final static String PATCH_HERO_FROM_ID_QUERY = "UPDATE hero SET name = :name, race = :race, enabled = :enabled, updated_at = :updated_at WHERE id = :id";

    private final static String DELETE_HERO_BY_ID_QUERY = "DELETE FROM hero WHERE id = :id";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    UUID create(Hero hero) {
        final Map<String, Object> params = Map.of("name", hero.getName(),
                "race", hero.getRace().name(),
                "powerStatsId", hero.getPowerStatsId());

        return namedParameterJdbcTemplate.queryForObject(
                CREATE_HERO_QUERY,
                params,
                UUID.class);
    }

    Hero findById(UUID id) {
        final SqlParameterSource params = new MapSqlParameterSource("id", id);
        List<Hero> heroes = namedParameterJdbcTemplate.query(FIND_HERO_BY_ID_QUERY, params, new HeroRowMapper());
        return heroes.stream().findFirst().orElse(null);
    }

    Hero findByName(String name) {
        final SqlParameterSource params = new MapSqlParameterSource("name", name);
        List<Hero> heroes = namedParameterJdbcTemplate.query(FIND_HERO_BY_NAME_QUERY, params, new HeroRowMapper());
        return  heroes.stream().findFirst().orElse(null);
    }

    void patch(Hero hero) {
        final SqlParameterSource params = new MapSqlParameterSource("id", hero.getId())
                .addValue("name", hero.getName())
                .addValue("race", hero.getRace().name())
                .addValue("enabled", hero.getEnabled())
                .addValue("updated_at", Timestamp.from(Instant.now()));

        namedParameterJdbcTemplate.update(PATCH_HERO_FROM_ID_QUERY, params);
    }

    void deleteById(UUID id) {
        final SqlParameterSource params = new MapSqlParameterSource("id", id);
        namedParameterJdbcTemplate.update(DELETE_HERO_BY_ID_QUERY, params);
    }
}
