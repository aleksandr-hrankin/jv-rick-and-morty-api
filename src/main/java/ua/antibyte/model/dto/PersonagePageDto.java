package ua.antibyte.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class PersonagePageDto {
    @JsonProperty("info")
    private PersonageInfoDto characterInfo;
    private List<PersonageRequestDto> results;
}
