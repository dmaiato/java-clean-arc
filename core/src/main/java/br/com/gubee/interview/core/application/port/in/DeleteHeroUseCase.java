package br.com.gubee.interview.core.application.port.in;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

public interface DeleteHeroUseCase {
    void execute(Command command);

    @Builder
    @Getter
    class Command {
        private final UUID id;
    }
}
