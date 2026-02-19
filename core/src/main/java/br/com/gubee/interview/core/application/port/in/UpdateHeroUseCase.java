package br.com.gubee.interview.core.application.port.in;

import br.com.gubee.interview.core.domain.model.Hero;
import br.com.gubee.interview.core.domain.model.Race;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

public interface UpdateHeroUseCase {
    Hero execute(Command command);

    @Builder
    @Getter
    class Command {
        private final UUID id;
        private final String name;
        private final Race race;
        private final Boolean enabled;
    }
}
