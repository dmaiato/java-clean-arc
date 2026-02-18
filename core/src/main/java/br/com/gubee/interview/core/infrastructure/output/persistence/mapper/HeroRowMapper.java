package br.com.gubee.interview.core.infrastructure.output.persistence.mapper;

import br.com.gubee.interview.core.infrastructure.output.persistence.entity.HeroEntity;
import br.com.gubee.interview.domain.model.Race;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class HeroRowMapper implements RowMapper<HeroEntity> {

    @Override
    public HeroEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        HeroEntity heroEntity = new HeroEntity();
        heroEntity.setId(rs.getObject("id", UUID.class));
        heroEntity.setName(rs.getString("name"));
        heroEntity.setRace(Race.valueOf(rs.getString("race")));
        heroEntity.setPowerStatsId(rs.getObject("power_stats_id", UUID.class));
        heroEntity.setCreatedAt(rs.getTimestamp("created_at").toInstant());
        heroEntity.setUpdatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toInstant() : null);
        heroEntity.setEnabled(rs.getBoolean("enabled"));
        return heroEntity;
    }
}
