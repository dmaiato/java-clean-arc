package br.com.gubee.interview.adapters.web.request;

import br.com.gubee.interview.core.application.port.in.UpdateHeroUseCase;
import br.com.gubee.interview.core.domain.model.Race;

import java.util.UUID;

public record UpdateHeroRequest(
    String name,
    Race race,
    Boolean enabled
) {
    public UpdateHeroUseCase.UpdateHeroCommand toCommand(UUID id) {
        return new UpdateHeroUseCase.UpdateHeroCommand(id, name, race, enabled);
    }
}
