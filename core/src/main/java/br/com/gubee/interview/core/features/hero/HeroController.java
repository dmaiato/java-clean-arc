package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.request.CreateHeroRequest;
import br.com.gubee.interview.model.request.ReturnHeroWithStatsRequest;
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

    private final HeroService heroService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Validated
                                       @RequestBody CreateHeroRequest createHeroRequest) {
        final UUID id = heroService.create(createHeroRequest);
        return created(URI.create(format("/api/v1/heroes/%s", id))).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReturnHeroWithStatsRequest> findById(@PathVariable UUID id) {
        final var hero = heroService.findWithStatsById(id);

        if (hero == null) {
            return  notFound().build();
        }

        return ResponseEntity.ok(hero);
    }

    @GetMapping()
    public ResponseEntity<ReturnHeroWithStatsRequest> findByName(@RequestParam String name) {
        final var hero = heroService.searchHeroWithStatsByName(name);
        if (hero == null) {
            return ok().build();
        }
        return ResponseEntity.ok(hero);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Hero> patchHero(@PathVariable UUID id, @RequestBody Hero partialHero) {
        final var hero = this.heroService.update(id, partialHero);
        if (hero == null) {
            return ok().build();
        }
        return ResponseEntity.ok(hero);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHero(@PathVariable UUID id) {
        this.heroService.deleteById(id);
        return noContent().build();
    }
}
