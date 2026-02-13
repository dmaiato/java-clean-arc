package br.com.gubee.interview.model;

import br.com.gubee.interview.model.enums.Race;
import br.com.gubee.interview.model.request.CreateHeroRequest;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE)
public class Hero {

    private UUID id;
    private String name;
    private Race race;
    private UUID powerStatsId;
    private Instant createdAt;
    private Instant updatedAt;
    private Boolean enabled;

    public Hero(CreateHeroRequest createHeroRequest, UUID powerStatsId) {
        this.name = createHeroRequest.getName();
        this.race = createHeroRequest.getRace();
        this.powerStatsId = powerStatsId;
    }
}
