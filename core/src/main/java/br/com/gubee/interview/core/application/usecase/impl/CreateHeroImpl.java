package br.com.gubee.interview.core.application.usecase.impl;

import br.com.gubee.interview.domain.model.Hero;
import br.com.gubee.interview.domain.model.PowerStats;
import br.com.gubee.interview.domain.command.CreateHeroCommand;
import br.com.gubee.interview.domain.repository.HeroRepository;
import br.com.gubee.interview.domain.repository.PowerStatsRepository;
import br.com.gubee.interview.domain.usecase.CreateHero;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CreateHeroImpl implements CreateHero {

    private final HeroRepository heroRepository;
    private final PowerStatsRepository powerStatsRepository;

    @Override
    @Transactional
    public UUID execute(CreateHeroCommand command) {
        var powerStats = PowerStats.create(command.getStrength(), command.getAgility(), command.getDexterity(), command.getIntelligence());
        powerStatsRepository.save(powerStats);

        var hero = Hero.create(command.getName(), command.getRace(), powerStats.getId());
        heroRepository.save(hero);

        return hero.getId();
    }
}
