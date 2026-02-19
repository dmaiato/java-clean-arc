package br.com.gubee.interview.core.application.port.out;

import java.util.UUID;

public interface DeletePowerStatsPort {
    void deleteById(UUID id);
}
