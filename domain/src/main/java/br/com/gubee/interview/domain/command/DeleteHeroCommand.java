package br.com.gubee.interview.domain.command;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class DeleteHeroCommand {
    private final UUID id;
}
