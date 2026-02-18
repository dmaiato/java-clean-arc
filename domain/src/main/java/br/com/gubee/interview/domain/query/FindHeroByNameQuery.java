package br.com.gubee.interview.domain.query;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindHeroByNameQuery {
    private final String name;
}
