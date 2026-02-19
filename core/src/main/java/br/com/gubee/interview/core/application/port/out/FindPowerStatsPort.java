package br.com.gubee.interview.core.application.port.out;

import br.com.gubee.interview.core.domain.model.PowerStats;
import java.util.Optional;
import java.util.UUID;

public interface FindPowerStatsPort {
    Optional<PowerStats> findById(UUID id);
}
