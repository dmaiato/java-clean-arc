package br.com.gubee.interview.domain.command;

import br.com.gubee.interview.domain.model.Race;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class UpdateHeroCommand {
    private final UUID id;
    private final String name;
    private final Race race;
    private final Boolean enabled;
}
