package org.example.literalura.repositoty;


import org.example.literalura.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {


    @Query("SELECT p FROM Person p WHERE :year between p.birthYear and p.deathYear")
    List<Person> findLivingAuthors(int year);
}
