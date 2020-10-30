package ua.antibyte.service;

import java.util.List;
import ua.antibyte.model.dto.PersonageResponseDto;

public interface PersonageService {
    List<PersonageResponseDto> getByName(String name);

    PersonageResponseDto getRandomPersonage();

    void saveAllPersonagesToDb();
}
