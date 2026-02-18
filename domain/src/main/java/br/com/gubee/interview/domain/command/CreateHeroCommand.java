package br.com.gubee.interview.domain.command;

import br.com.gubee.interview.domain.model.Race;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateHeroCommand {
    private final String name;
    private final Race race;
    private final int strength;
    private final int agility;
    private final int dexterity;
    private final int intelligence;
}
