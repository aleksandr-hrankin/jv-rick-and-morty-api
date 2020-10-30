package ua.antibyte.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.antibyte.model.dto.PersonageResponseDto;
import ua.antibyte.service.PersonageService;

@RestController
@RequestMapping("/personages")
public class PersonageController {
    private final PersonageService personageService;

    public PersonageController(PersonageService personageService) {
        this.personageService = personageService;
    }

    @GetMapping("/random")
    public PersonageResponseDto getRandomPersonage() {
        return personageService.getRandomPersonage();
    }

    @GetMapping("/search")
    public List<PersonageResponseDto> getByName(@RequestParam String name) {
        return personageService.getByName(name);
    }

    @GetMapping("/save-all")
    public String save() {
        personageService.saveAllPersonagesToDb();
        return "Save all personages successful";
    }
}
