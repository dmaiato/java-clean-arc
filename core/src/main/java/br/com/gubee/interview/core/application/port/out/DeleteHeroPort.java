package br.com.gubee.interview.core.application.port.out;

import java.util.UUID;

public interface DeleteHeroPort {
    void deleteById(UUID id);
}
