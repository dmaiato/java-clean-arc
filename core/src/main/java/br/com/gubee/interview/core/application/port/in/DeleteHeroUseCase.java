package br.com.gubee.interview.core.application.port.in;

import java.util.UUID;

public interface DeleteHeroUseCase {
    void execute(DeleteHeroCommand command);

    record DeleteHeroCommand(UUID id) {
    }
}
