package ua.antibyte.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Random;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Service;
import ua.antibyte.model.dto.CharacterDto;
import ua.antibyte.model.dto.CharacterPageDto;
import ua.antibyte.service.CharacterService;

@Service
public class CharacterServiceImpl implements CharacterService {
    private static final String ALL_CHARS_URL = "https://rickandmortyapi.com/api/character/";
    private static final String CHAR_BY_ID_URL = "https://rickandmortyapi.com/api/character/%s";
    private final CloseableHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public CharacterServiceImpl(CloseableHttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public CharacterDto getRandomCharacter() {
        String url = String.format(CHAR_BY_ID_URL, getRandomValueFromMaxCharacter());
        HttpGet httpGet = new HttpGet(url);
        try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
            return objectMapper.readValue(httpResponse.getEntity().getContent(),
                    CharacterDto.class);
        } catch (IOException e) {
            throw new RuntimeException("Can't sent request to " + url, e);
        }
    }

    private int getNumberOfCharacters() {
        HttpGet httpGet = new HttpGet(ALL_CHARS_URL);
        try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
            CharacterPageDto characterPage = objectMapper.readValue(
                    httpResponse.getEntity().getContent(), CharacterPageDto.class);
            return characterPage.getCharacterInfo().getCount();
        } catch (IOException e) {
            throw new RuntimeException("Can't sent request to " + ALL_CHARS_URL, e);
        }
    }

    private int getRandomValueFromMaxCharacter() {
        return new Random().nextInt(getNumberOfCharacters());
    }
}
