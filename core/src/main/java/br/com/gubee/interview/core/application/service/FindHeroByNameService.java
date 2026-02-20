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
    public HeroWithStatsResult execute(String name) {
        var hero = findHeroPort.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Hero not found with name: " + name));

        var stats = findPowerStatsPort.findById(hero.getPowerStatsId())
                .orElseThrow(() -> new NoSuchElementException("PowerStats not found for Hero name: " + name));

        return new HeroWithStatsResult(hero, stats);
    }
}
