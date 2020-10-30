package ua.antibyte.service.mapper;

import org.springframework.stereotype.Component;
import ua.antibyte.model.Personage;
import ua.antibyte.model.dto.PersonageRequestDto;
import ua.antibyte.model.dto.PersonageResponseDto;

@Component
public class PersonageMapper {
    public Personage mapRequestDtoToPersonage(PersonageRequestDto personageDto) {
        Personage personage = new Personage();
        personage.setName(personageDto.getName());
        personage.setStatus(personageDto.getStatus());
        personage.setSpecies(personageDto.getSpecies());
        personage.setType(personageDto.getType());
        personage.setGender(personageDto.getGender());
        return personage;
    }

    public PersonageResponseDto mapPersonageToResponseDto(Personage personage) {
        PersonageResponseDto personageDto = new PersonageResponseDto();
        personageDto.setId(personage.getId());
        personageDto.setName(personage.getName());
        personageDto.setStatus(personage.getStatus());
        personageDto.setType(personage.getType());
        personageDto.setGender(personage.getGender());
        return personageDto;
    }
}
