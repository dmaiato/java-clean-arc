package br.com.gubee.interview.core.application.usecase.impl;

import br.com.gubee.interview.domain.model.Hero;
import br.com.gubee.interview.domain.command.UpdateHeroCommand;
import br.com.gubee.interview.domain.repository.HeroRepository;
import br.com.gubee.interview.domain.usecase.UpdateHero;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class UpdateHeroImpl implements UpdateHero {

    private final HeroRepository heroRepository;

    @Override
    @Transactional
    public Hero execute(UpdateHeroCommand command) {
        var hero = heroRepository.findById(command.getId())
                .orElseThrow(() -> new NoSuchElementException("Hero not found with ID: " + command.getId()));

        if (command.getName() != null && command.getRace() != null) {
            hero.updateDetails(command.getName(), command.getRace());
        }

        if (command.getEnabled() != null) {
            if (command.getEnabled()) {
                hero.enable();
            } else {
                hero.disable();
            }
        }

        heroRepository.patch(hero);
        return hero;
    }
}
