package br.com.gubee.interview.core.application.usecase.impl;

import br.com.gubee.interview.domain.result.HeroWithStatsResult;
import br.com.gubee.interview.domain.query.FindHeroByIdQuery;
import br.com.gubee.interview.domain.repository.HeroRepository;
import br.com.gubee.interview.domain.repository.PowerStatsRepository;
import br.com.gubee.interview.domain.usecase.FindHeroById;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class FindHeroByIdImpl implements FindHeroById {

    private final HeroRepository heroRepository;
    private final PowerStatsRepository powerStatsRepository;

    @Override
    @Transactional(readOnly = true)
    public HeroWithStatsResult execute(FindHeroByIdQuery query) {
        var hero = heroRepository.findById(query.getId())
                .orElseThrow(() -> new NoSuchElementException("Hero not found with ID: " + query.getId()));

        var stats = powerStatsRepository.findById(hero.getPowerStatsId())
                .orElseThrow(() -> new NoSuchElementException("PowerStats not found for Hero ID: " + query.getId()));
        
        return new HeroWithStatsResult(hero, stats);
    }
}
