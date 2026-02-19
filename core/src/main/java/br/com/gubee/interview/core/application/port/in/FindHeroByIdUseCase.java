package br.com.gubee.interview.core.application.port.in;

import br.com.gubee.interview.core.domain.result.HeroWithStatsResult;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

public interface FindHeroByIdUseCase {
    HeroWithStatsResult execute(Query query);

    @Builder
    @Getter
    class Query {
        private final UUID id;
    }
}
