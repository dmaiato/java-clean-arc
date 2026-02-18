package br.com.gubee.interview.domain.usecase;

import br.com.gubee.interview.domain.command.DeleteHeroCommand;

public interface DeleteHero {
    void execute(DeleteHeroCommand command);
}
