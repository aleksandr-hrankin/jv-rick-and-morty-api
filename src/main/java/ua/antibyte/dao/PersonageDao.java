package ua.antibyte.dao;

import java.util.List;
import ua.antibyte.model.Personage;

public interface PersonageDao {
    void addAll(List<Personage> personages);

    Personage findById(Long id);

    List<Personage> findByName(String name);

    Long getCountOfPersonages();
}
