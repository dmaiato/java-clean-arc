package br.com.gubee.interview.domain.query;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class FindHeroByIdQuery {
    private final UUID id;
}
