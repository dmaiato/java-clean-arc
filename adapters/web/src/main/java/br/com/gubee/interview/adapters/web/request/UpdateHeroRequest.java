package br.com.gubee.interview.adapters.web.request;

import br.com.gubee.interview.core.application.port.in.UpdateHeroUseCase;
import br.com.gubee.interview.core.domain.model.Race;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateHeroRequest {
    private String name;
    private Race race;
    private Boolean enabled;

    public UpdateHeroUseCase.Command toCommand(UUID id) {
        return UpdateHeroUseCase.Command.builder()
                .id(id)
                .name(this.name)
                .race(this.race)
                .enabled(this.enabled)
                .build();
    }
}
