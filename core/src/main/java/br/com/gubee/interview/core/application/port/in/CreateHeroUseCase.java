package br.com.gubee.interview.core.application.port.in;

import br.com.gubee.interview.core.domain.model.Race;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

public interface CreateHeroUseCase {
    UUID execute(Command command);

    @Builder
    @Getter
    class Command {
        private final String name;
        private final Race race;
        private final int strength;
        private final int agility;
        private final int dexterity;
        private final int intelligence;
    }
}
