package ua.antibyte.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "personages")
public class Personage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personage_id")
    private Long id;
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
}
