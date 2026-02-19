package br.com.gubee.interview.core.application.port.out;

import br.com.gubee.interview.core.domain.model.Hero;

public interface UpdateHeroPort {
    void patch(Hero hero);
}
