package br.com.gubee.interview.adapters.web.request;

import br.com.gubee.interview.core.application.port.in.CreateHeroUseCase;
import br.com.gubee.interview.core.domain.model.Race;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Builder
public record CreateHeroRequest(
    @NotBlank(message = "message.name.mandatory")
    @Length(min = 1, max = 255, message = "message.name.length")
    String name,

    @NotNull(message = "message.race.mandatory")
    Race race,

    @Min(value = 0, message = "message.powerstats.strength.min")
    @Max(value = 10, message = "message.powerstats.strength.max")
    @NotNull(message = "message.powerstats.strength.mandatory")
    int strength,

    @Min(value = 0, message = "message.powerstats.agility.min")
    @Max(value = 10, message = "message.powerstats.agility.max")
    @NotNull(message = "message.powerstats.agility.mandatory")
    int agility,

    @Min(value = 0, message = "message.powerstats.dexterity.min")
    @Max(value = 10, message = "message.powerstats.dexterity.max")
    @NotNull(message = "message.powerstats.dexterity.mandatory")
    int dexterity,

    @Min(value = 0, message = "message.powerstats.intelligence.min")
    @Max(value = 10, message = "message.powerstats.intelligence.max")
    @NotNull(message = "message.powerstats.intelligence.mandatory")
    int intelligence
) {
    public CreateHeroUseCase.CreateHeroCommand toCommand() {
        return new CreateHeroUseCase.CreateHeroCommand(name, race, strength, agility, dexterity, intelligence);
    }
}
