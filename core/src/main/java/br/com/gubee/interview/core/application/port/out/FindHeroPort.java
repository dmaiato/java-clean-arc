package br.com.gubee.interview.core.application.port.out;

import br.com.gubee.interview.core.domain.model.Hero;
import java.util.Optional;
import java.util.UUID;

public interface FindHeroPort {
    Optional<Hero> findById(UUID id);
    Optional<Hero> findByName(String name);
}
