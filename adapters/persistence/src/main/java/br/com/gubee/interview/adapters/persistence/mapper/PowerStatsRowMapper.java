package br.com.gubee.interview.adapters.persistence.mapper;

import br.com.gubee.interview.adapters.persistence.entity.PowerStatsEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PowerStatsRowMapper implements RowMapper<PowerStatsEntity> {

    @Override
    public PowerStatsEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        PowerStatsEntity powerStatsEntity = new PowerStatsEntity();
        powerStatsEntity.setId(rs.getObject("id", UUID.class));
        powerStatsEntity.setStrength(rs.getInt("strength"));
        powerStatsEntity.setAgility(rs.getInt("agility"));
        powerStatsEntity.setDexterity(rs.getInt("dexterity"));
        powerStatsEntity.setIntelligence(rs.getInt("intelligence"));
        powerStatsEntity.setCreatedAt(rs.getTimestamp("created_at").toInstant());
        powerStatsEntity.setUpdatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toInstant() : null);
        return powerStatsEntity;
    }
}
