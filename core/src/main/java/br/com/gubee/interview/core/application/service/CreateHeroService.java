package br.com.gubee.interview.core.application.service;

import br.com.gubee.interview.core.application.port.in.CreateHeroUseCase;
import br.com.gubee.interview.core.application.port.out.SaveHeroPort;
import br.com.gubee.interview.core.application.port.out.SavePowerStatsPort;
import br.com.gubee.interview.core.domain.model.Hero;
import br.com.gubee.interview.core.domain.model.PowerStats;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateHeroService implements CreateHeroUseCase {

    private final SaveHeroPort saveHeroPort;
    private final SavePowerStatsPort savePowerStatsPort;

    @Override
    @Transactional
    public UUID execute(CreateHeroCommand command) {
        var powerStats = PowerStats.create(
                command.strength(),
                command.agility(),
                command.dexterity(),
                command.intelligence()
        );
        savePowerStatsPort.save(powerStats);

        var hero = Hero.create(command.name(), command.race(), powerStats.getId());
        saveHeroPort.save(hero);

        return hero.getId();
    }
}
