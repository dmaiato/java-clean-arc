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
    public Hero execute(UpdateHeroCommand command) {
        var hero = findHeroPort.findById(command.id())
                .orElseThrow(() -> new NoSuchElementException("Hero not found with ID: " + command.id()));

        if (command.name() != null && command.race() != null) {
            hero.updateDetails(command.name(), command.race());
        }

        if (command.enabled() != null) {
            if (command.enabled()) {
                hero.enable();
            } else {
                hero.disable();
            }
        }

        updateHeroPort.patch(hero);
        return hero;
    }
}
