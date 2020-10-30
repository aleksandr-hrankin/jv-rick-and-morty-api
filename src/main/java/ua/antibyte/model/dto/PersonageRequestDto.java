package ua.antibyte.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonageRequestDto {
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
}
