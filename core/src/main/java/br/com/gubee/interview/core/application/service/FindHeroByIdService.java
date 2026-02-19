package br.com.gubee.interview.core.application.service;

import br.com.gubee.interview.core.application.port.in.FindHeroByIdUseCase;
import br.com.gubee.interview.core.application.port.out.FindHeroPort;
import br.com.gubee.interview.core.application.port.out.FindPowerStatsPort;
import br.com.gubee.interview.core.domain.result.HeroWithStatsResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class FindHeroByIdService implements FindHeroByIdUseCase {

    private final FindHeroPort findHeroPort;
    private final FindPowerStatsPort findPowerStatsPort;

    @Override
    @Transactional(readOnly = true)
    public HeroWithStatsResult execute(Query query) {
        var hero = findHeroPort.findById(query.getId())
                .orElseThrow(() -> new NoSuchElementException("Hero not found with ID: " + query.getId()));

        var stats = findPowerStatsPort.findById(hero.getPowerStatsId())
                .orElseThrow(() -> new NoSuchElementException("PowerStats not found for Hero ID: " + query.getId()));

        return new HeroWithStatsResult(hero, stats);
    }
}
