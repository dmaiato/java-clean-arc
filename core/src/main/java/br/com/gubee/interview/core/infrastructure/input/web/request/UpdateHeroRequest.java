package br.com.gubee.interview.core.infrastructure.input.web.request;

import br.com.gubee.interview.domain.command.UpdateHeroCommand;
import br.com.gubee.interview.domain.model.Race;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateHeroRequest {
    private String name;
    private Race race;
    private Boolean enabled;

    public UpdateHeroCommand toCommand(UUID id) {
        return UpdateHeroCommand.builder()
                .id(id)
                .name(this.name)
                .race(this.race)
                .enabled(this.enabled)
                .build();
    }
}
