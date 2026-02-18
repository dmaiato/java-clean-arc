package br.com.gubee.interview.domain.usecase;

import br.com.gubee.interview.domain.result.HeroWithStatsResult;
import br.com.gubee.interview.domain.query.FindHeroByNameQuery;

public interface FindHeroByName {
    HeroWithStatsResult execute(FindHeroByNameQuery query);
}
