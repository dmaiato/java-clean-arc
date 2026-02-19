package br.com.gubee.interview.core.application.service;

import br.com.gubee.interview.core.application.port.in.UpdateHeroUseCase;
import br.com.gubee.interview.core.application.port.out.FindHeroPort;
import br.com.gubee.interview.core.application.port.out.UpdateHeroPort;
import br.com.gubee.interview.core.domain.model.Hero;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UpdateHeroService implements UpdateHeroUseCase {

    private final FindHeroPort findHeroPort;
    private final UpdateHeroPort updateHeroPort;

    @Override
    @Transactional
    public Hero execute(Command command) {
        var hero = findHeroPort.findById(command.getId())
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

        updateHeroPort.patch(hero);
        return hero;
    }
}
