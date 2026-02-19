package br.com.gubee.interview.core.application.port.out;

import br.com.gubee.interview.core.domain.model.PowerStats;

public interface SavePowerStatsPort {
    PowerStats save(PowerStats powerStats);
}
