package br.com.gubee.interview.domain.repository;

import br.com.gubee.interview.domain.model.Hero;
import java.util.Optional;
import java.util.UUID;

public interface HeroRepository {
    Hero save(Hero hero);
    Optional<Hero> findById(UUID id);
    Optional<Hero> findByName(String name);
    void deleteById(UUID id);
    void patch(Hero hero);
}
