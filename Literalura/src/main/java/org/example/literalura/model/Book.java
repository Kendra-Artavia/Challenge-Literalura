package org.example.literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "books")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {

    @Id
    private Long id;
    @Column(length = 1000)
    private String title;

    @JsonProperty("authors")
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> authors;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "book_languages", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "language", length = 20)
    private List<String> languages;




    @JsonProperty("download_count")
    private Integer downloadCount;


    public Book() {}

    public Book(Long id, String title, List<Person> authors,
                List<String> languages, Integer downloadCount) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.languages = languages;
        this.downloadCount = downloadCount;
    }

    @Override
    public String toString() {
        return "\n📚 Book Details:" +
                "\n  • ID: " + id +
                "\n  • Title: " + title +
                "\n  • Downloads: " + downloadCount +
                "\n  • Authors: " + (authors != null
                ? authors.stream()
                .map(Person::getName)
                .toList()
                : "[]") +
                "\n  • Languages: " + languages + "\n";
    }




}
