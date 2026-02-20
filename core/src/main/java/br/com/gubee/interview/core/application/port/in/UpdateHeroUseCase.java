package br.com.gubee.interview.core.application.port.in;

import br.com.gubee.interview.core.domain.model.Hero;
import br.com.gubee.interview.core.domain.model.Race;
import lombok.Builder;

import java.util.UUID;

public interface UpdateHeroUseCase {
    Hero execute(UpdateHeroCommand command);

    record UpdateHeroCommand(UUID id, String name, Race race, Boolean enabled) {
    }
}
