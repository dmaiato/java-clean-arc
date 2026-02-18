package br.com.gubee.interview.core.infrastructure.input.web.controller;

import br.com.gubee.interview.domain.model.Hero;
import br.com.gubee.interview.domain.command.DeleteHeroCommand;
import br.com.gubee.interview.domain.command.UpdateHeroCommand;
import br.com.gubee.interview.domain.query.FindHeroByIdQuery;
import br.com.gubee.interview.domain.query.FindHeroByNameQuery;
import br.com.gubee.interview.domain.usecase.*;
import br.com.gubee.interview.core.infrastructure.input.web.request.CreateHeroRequest;
import br.com.gubee.interview.core.infrastructure.input.web.request.UpdateHeroRequest;
import br.com.gubee.interview.core.infrastructure.input.web.response.HeroWithStatsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

import static java.lang.String.format;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/heroes", produces = APPLICATION_JSON_VALUE)
public class HeroController {

    private final CreateHero createHeroUseCase;
    private final FindHeroById findHeroByIdUseCase;
    private final FindHeroByName findHeroByNameUseCase;
    private final UpdateHero updateHeroUseCase;
    private final DeleteHero deleteHeroUseCase;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Validated
                                       @RequestBody CreateHeroRequest createHeroRequest) {
        var command = createHeroRequest.toCommand();
        final UUID id = createHeroUseCase.execute(command);
        return created(URI.create(format("/api/v1/heroes/%s", id))).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HeroWithStatsResponse> findById(@PathVariable UUID id) {
        var query = FindHeroByIdQuery.builder().id(id).build();
        var output = findHeroByIdUseCase.execute(query);
        return ResponseEntity.ok(HeroWithStatsResponse.from(output));
    }

    @GetMapping()
    public ResponseEntity<HeroWithStatsResponse> findByName(@RequestParam String name) {
        var query = FindHeroByNameQuery.builder().name(name).build();
        var output = findHeroByNameUseCase.execute(query);
        return ResponseEntity.ok(HeroWithStatsResponse.from(output));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Hero> patchHero(@PathVariable UUID id, @RequestBody UpdateHeroRequest request) {
        var command = request.toCommand(id);
        final var hero = updateHeroUseCase.execute(command);
        return ResponseEntity.ok(hero);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHero(@PathVariable UUID id) {
        var command = DeleteHeroCommand.builder().id(id).build();
        deleteHeroUseCase.execute(command);
        return noContent().build();
    }
}
