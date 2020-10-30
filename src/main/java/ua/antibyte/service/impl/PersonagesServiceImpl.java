package ua.antibyte.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Service;
import ua.antibyte.dao.PersonageDao;
import ua.antibyte.model.Personage;
import ua.antibyte.model.dto.PersonagePageDto;
import ua.antibyte.model.dto.PersonageRequestDto;
import ua.antibyte.model.dto.PersonageResponseDto;
import ua.antibyte.service.PersonageService;
import ua.antibyte.service.mapper.PersonageMapper;

@Service
public class PersonagesServiceImpl implements PersonageService {
    private static final String ALL_CHARS_FIRST_PAGE_URL = "https://rickandmortyapi.com/api/character/";
    private static final String CHARS_PAGE_URL = "https://rickandmortyapi.com/api/character/?page=%s";
    private final CloseableHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final PersonageDao personageDao;
    private final PersonageMapper personageMapper;

    public PersonagesServiceImpl(CloseableHttpClient httpClient,
                                 ObjectMapper objectMapper,
                                 PersonageDao personageDao,
                                 PersonageMapper personageMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.personageDao = personageDao;
        this.personageMapper = personageMapper;
    }

    @Override
    public List<PersonageResponseDto> getByName(String name) {
        return personageDao.findByName(name).stream()
                .map(personageMapper::mapPersonageToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public PersonageResponseDto getRandomPersonage() {
        Long randomId = getRandomIdFromMaxCharacter();
        Personage personage = personageDao.findById(randomId);
        return personageMapper.mapPersonageToResponseDto(personage);
    }

    @Override
    public void saveAllPersonagesToDb() {
        PersonagePageDto page = getData(ALL_CHARS_FIRST_PAGE_URL, PersonagePageDto.class);
        List<PersonageRequestDto> personageDtos = page.getResults();
        int countOfPages = page.getCharacterInfo().getPages();
        for (int i = 1; i <= countOfPages; i++) {
            String url = String.format(CHARS_PAGE_URL, i);
            List<PersonageRequestDto> personages
                    = getData(url, PersonagePageDto.class).getResults();
            personageDtos.addAll(personages);
        }
        List<Personage> personages = personageDtos.stream()
                .map(personageMapper::mapRequestDtoToPersonage)
                .collect(Collectors.toList());
        personageDao.addAll(personages);
    }

    private <T> T getData(String url, Class<T> clazz) {
        HttpGet httpGet = new HttpGet(url);
        try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
            return objectMapper.readValue(
                    httpResponse.getEntity().getContent(), clazz);
        } catch (IOException e) {
            throw new RuntimeException("Can't sent request to " + url, e);
        }
    }

    private Long getRandomIdFromMaxCharacter() {
        Long countOfPersonages = personageDao.getCountOfPersonages();
        return ThreadLocalRandom.current().nextLong(countOfPersonages);
    }
}
