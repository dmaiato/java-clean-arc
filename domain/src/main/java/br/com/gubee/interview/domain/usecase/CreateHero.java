package br.com.gubee.interview.domain.usecase;

import br.com.gubee.interview.domain.command.CreateHeroCommand;
import java.util.UUID;

public interface CreateHero {
    UUID execute(CreateHeroCommand command);
}
