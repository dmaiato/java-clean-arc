package br.com.gubee.interview.core.application.usecase.impl;

import br.com.gubee.interview.domain.command.DeleteHeroCommand;
import br.com.gubee.interview.domain.repository.HeroRepository;
import br.com.gubee.interview.domain.repository.PowerStatsRepository;
import br.com.gubee.interview.domain.usecase.DeleteHero;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class DeleteHeroImpl implements DeleteHero {

    private final HeroRepository heroRepository;
    private final PowerStatsRepository powerStatsRepository;

    @Override
    @Transactional
    public void execute(DeleteHeroCommand command) {
        var hero = heroRepository.findById(command.getId())
                .orElseThrow(() -> new NoSuchElementException("Hero not found with ID: " + command.getId()));

        heroRepository.deleteById(hero.getId());
        powerStatsRepository.deleteById(hero.getPowerStatsId());
    }
}
