package br.com.gubee.interview.core.application.port.in;

import br.com.gubee.interview.core.domain.model.Race;

import java.util.UUID;

public interface CreateHeroUseCase {
    UUID execute(CreateHeroCommand command);

    record CreateHeroCommand(
            String name,
            Race race,
            int strength,
            int agility,
            int dexterity,
            int intelligence
    ) {
    }
}
