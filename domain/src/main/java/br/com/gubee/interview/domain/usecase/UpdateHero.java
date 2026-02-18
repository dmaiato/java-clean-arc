package br.com.gubee.interview.domain.usecase;

import br.com.gubee.interview.domain.model.Hero;
import br.com.gubee.interview.domain.command.UpdateHeroCommand;

public interface UpdateHero {
    Hero execute(UpdateHeroCommand command);
}
