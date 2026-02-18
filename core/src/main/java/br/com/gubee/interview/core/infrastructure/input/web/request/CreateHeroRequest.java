package br.com.gubee.interview.core.infrastructure.input.web.request;

import br.com.gubee.interview.domain.command.CreateHeroCommand;
import br.com.gubee.interview.domain.model.Race;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE)
public class CreateHeroRequest {

    @NotBlank(message = "message.name.mandatory")
    @Length(min = 1, max = 255, message = "message.name.length")
    private String name;

    @NotNull(message = "message.race.mandatory")
    private Race race;

    @Min(value = 0, message = "message.powerstats.strength.min")
    @Max(value = 10, message = "message.powerstats.strength.max")
    @NotNull(message = "message.powerstats.strength.mandatory")
    private int strength;

    @Min(value = 0, message = "message.powerstats.agility.min")
    @Max(value = 10, message = "message.powerstats.agility.max")
    @NotNull(message = "message.powerstats.agility.mandatory")
    private int agility;

    @Min(value = 0, message = "message.powerstats.dexterity.min")
    @Max(value = 10, message = "message.powerstats.dexterity.max")
    @NotNull(message = "message.powerstats.dexterity.mandatory")
    private int dexterity;

    @Min(value = 0, message = "message.powerstats.intelligence.min")
    @Max(value = 10, message = "message.powerstats.intelligence.max")
    @NotNull(message = "message.powerstats.intelligence.mandatory")
    private int intelligence;

    public CreateHeroCommand toCommand() {
        return CreateHeroCommand.builder()
                .name(this.name)
                .race(this.race)
                .strength(this.strength)
                .agility(this.agility)
                .dexterity(this.dexterity)
                .intelligence(this.intelligence)
                .build();
    }
}
