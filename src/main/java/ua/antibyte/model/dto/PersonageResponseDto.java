package ua.antibyte.model.dto;

import lombok.Data;

@Data
public class PersonageResponseDto {
    private Long id;
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
}
