package ua.antibyte.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CharacterPageDto {
    @JsonProperty("info")
    private CharacterInfoDto characterInfo;
}
