package br.com.gubee.interview.core.application.service;

import br.com.gubee.interview.core.application.port.in.FindHeroByNameUseCase;
import br.com.gubee.interview.core.application.port.out.FindHeroPort;
import br.com.gubee.interview.core.application.port.out.FindPowerStatsPort;
import br.com.gubee.interview.core.domain.result.HeroWithStatsResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class FindHeroByNameService implements FindHeroByNameUseCase {

    private final FindHeroPort findHeroPort;
    private final FindPowerStatsPort findPowerStatsPort;

    @Override
    @Transactional(readOnly = true)
    public HeroWithStatsResult execute(Query query) {
        var hero = findHeroPort.findByName(query.getName())
                .orElseThrow(() -> new NoSuchElementException("Hero not found with name: " + query.getName()));

        var stats = findPowerStatsPort.findById(hero.getPowerStatsId())
                .orElseThrow(() -> new NoSuchElementException("PowerStats not found for Hero name: " + query.getName()));

        return new HeroWithStatsResult(hero, stats);
    }
}
