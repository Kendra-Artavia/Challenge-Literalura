package org.example.literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Setter
@Getter
@Entity
@Table(name = "persons")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JsonProperty("birth_year")
    private Integer birthYear;

    @JsonProperty("death_year")
    private Integer deathYear;
    private String name;

    public Person() {}



    @Override
    public String toString() {
        String vida = (birthYear != null ? birthYear : "?") +
                " - " +
                (deathYear != null ? deathYear : "?");

        return "\n👤 Author:" +
                "\n  • Name: " + name +
                "\n  • Lifespan: " + vida + "\n";
    }

}
