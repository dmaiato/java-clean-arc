package br.com.gubee.interview.core.application.service;

import br.com.gubee.interview.core.application.port.in.DeleteHeroUseCase;
import br.com.gubee.interview.core.application.port.out.DeleteHeroPort;
import br.com.gubee.interview.core.application.port.out.DeletePowerStatsPort;
import br.com.gubee.interview.core.application.port.out.FindHeroPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DeleteHeroService implements DeleteHeroUseCase {

    private final FindHeroPort findHeroPort;
    private final DeleteHeroPort deleteHeroPort;
    private final DeletePowerStatsPort deletePowerStatsPort;

    @Override
    @Transactional
    public void execute(Command command) {
        var hero = findHeroPort.findById(command.getId())
                .orElseThrow(() -> new NoSuchElementException("Hero not found with ID: " + command.getId()));

        deleteHeroPort.deleteById(hero.getId());
        deletePowerStatsPort.deleteById(hero.getPowerStatsId());
    }
}
