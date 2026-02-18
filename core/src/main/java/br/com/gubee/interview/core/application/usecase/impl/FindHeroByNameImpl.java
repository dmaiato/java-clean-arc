package br.com.gubee.interview.core.application.usecase.impl;

import br.com.gubee.interview.domain.result.HeroWithStatsResult;
import br.com.gubee.interview.domain.query.FindHeroByNameQuery;
import br.com.gubee.interview.domain.repository.HeroRepository;
import br.com.gubee.interview.domain.repository.PowerStatsRepository;
import br.com.gubee.interview.domain.usecase.FindHeroByName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class FindHeroByNameImpl implements FindHeroByName {

    private final HeroRepository heroRepository;
    private final PowerStatsRepository powerStatsRepository;

    @Override
    @Transactional(readOnly = true)
    public HeroWithStatsResult execute(FindHeroByNameQuery query) {
        var hero = heroRepository.findByName(query.getName())
                .orElseThrow(() -> new NoSuchElementException("Hero not found with name: " + query.getName()));

        var stats = powerStatsRepository.findById(hero.getPowerStatsId())
                .orElseThrow(() -> new NoSuchElementException("PowerStats not found for Hero name: " + query.getName()));
        
        return new HeroWithStatsResult(hero, stats);
    }
}
