package org.example.literalura.repositoty;


import org.example.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository  extends JpaRepository<Book, Long> {



    @Query("SELECT DISTINCT b FROM Book b " +
            "LEFT JOIN FETCH b.authors ")
    List<Book> findAllWithDetails();


    List<Book> findByLanguagesContaining(String language);

    Book findByTitle(String title);


    @Query("SELECT b FROM Book b JOIN b.authors a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Book> findByAuthorNameContaining(@Param("name") String name);


    Book findTop1ByOrderByDownloadCountDesc();
}
