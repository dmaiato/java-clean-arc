package br.com.gubee.interview.core.application.port.in;

import br.com.gubee.interview.core.domain.result.HeroWithStatsResult;
import lombok.Builder;
import lombok.Getter;

public interface FindHeroByNameUseCase {
    HeroWithStatsResult execute(Query query);

    @Builder
    @Getter
    class Query {
        private final String name;
    }
}
