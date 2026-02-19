package br.com.gubee.interview.adapters.web.controller;

import br.com.gubee.interview.core.application.port.in.*;
import br.com.gubee.interview.core.domain.model.Hero;
import br.com.gubee.interview.adapters.web.request.CreateHeroRequest;
import br.com.gubee.interview.adapters.web.request.UpdateHeroRequest;
import br.com.gubee.interview.adapters.web.response.HeroWithStatsResponse;
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

    private final CreateHeroUseCase createHeroUseCase;
    private final FindHeroByIdUseCase findHeroByIdUseCase;
    private final FindHeroByNameUseCase findHeroByNameUseCase;
    private final UpdateHeroUseCase updateHeroUseCase;
    private final DeleteHeroUseCase deleteHeroUseCase;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Validated
                                       @RequestBody CreateHeroRequest createHeroRequest) {
        var command = createHeroRequest.toCommand();
        final UUID id = createHeroUseCase.execute(command);
        return created(URI.create(format("/api/v1/heroes/%s", id))).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HeroWithStatsResponse> findById(@PathVariable UUID id) {
        var query = FindHeroByIdUseCase.Query.builder().id(id).build();
        var output = findHeroByIdUseCase.execute(query);
        return ResponseEntity.ok(HeroWithStatsResponse.from(output));
    }

    @GetMapping()
    public ResponseEntity<HeroWithStatsResponse> findByName(@RequestParam String name) {
        var query = FindHeroByNameUseCase.Query.builder().name(name).build();
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
        var command = DeleteHeroUseCase.Command.builder().id(id).build();
        deleteHeroUseCase.execute(command);
        return noContent().build();
    }
}
