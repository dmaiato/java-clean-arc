package br.com.gubee.interview.domain.usecase;

import br.com.gubee.interview.domain.result.HeroWithStatsResult;
import br.com.gubee.interview.domain.query.FindHeroByIdQuery;

public interface FindHeroById {
    HeroWithStatsResult execute(FindHeroByIdQuery query);
}
