package br.com.gubee.interview.core.application.service;

import br.com.gubee.interview.core.application.port.in.FindHeroByIdUseCase;
import br.com.gubee.interview.core.application.port.out.FindHeroPort;
import br.com.gubee.interview.core.application.port.out.FindPowerStatsPort;
import br.com.gubee.interview.core.domain.result.HeroWithStatsResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindHeroByIdService implements FindHeroByIdUseCase {

    private final FindHeroPort findHeroPort;
    private final FindPowerStatsPort findPowerStatsPort;

    @Override
    @Transactional(readOnly = true)
    public HeroWithStatsResult execute(UUID id) {
        var hero = findHeroPort.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Hero not found with ID: " + id));

        var stats = findPowerStatsPort.findById(hero.getPowerStatsId())
                .orElseThrow(() -> new NoSuchElementException("PowerStats not found for Hero ID: " + id));

        return new HeroWithStatsResult(hero, stats);
    }
}
