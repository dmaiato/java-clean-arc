package br.com.gubee.interview.core.features.powerstats;

import br.com.gubee.interview.model.PowerStats;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PowerStatsService {

    private final PowerStatsRepository powerStatsRepository;

    @Transactional
    public UUID create(PowerStats powerStats) {
        return powerStatsRepository.create(powerStats);
    }

    @Transactional(readOnly = true)
    public PowerStats findById(UUID id) {
        return powerStatsRepository.findById(id);
    }

    @Transactional()
    public void deleteById(UUID id) {
        var powerStats = powerStatsRepository.findById(id);
        powerStatsRepository.deleteById(powerStats.getId());
    }
}
