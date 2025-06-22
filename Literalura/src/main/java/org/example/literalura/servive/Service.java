package org.example.literalura.servive;


import lombok.RequiredArgsConstructor;
import org.example.literalura.model.Book;
import org.example.literalura.model.Person;
import org.example.literalura.repositoty.BookRepository;
import org.example.literalura.repositoty.PersonRepository;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class Service {

    private final BookRepository bookRepository;
    private final PersonRepository personRepository;



    public boolean addBook(Book book) {
        if (book == null || book.getId() == null) {
            throw new IllegalArgumentException("Book or Book ID cannot be null.");
        }

        if (bookRepository.existsById(book.getId())) {
            System.out.println("Book with ID " + book.getId() + " already exists.");
            return false;
        }

        try {
            bookRepository.save(book);
            return true;
        } catch (Exception e) {
            System.err.println("Error saving book: " + e.getMessage());
            throw new RuntimeException("Failed to save book to database", e);
        }
    }


    public List<Book> getAllBooksWithDetails() {
        return bookRepository.findAllWithDetails();
    }


    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public List<Person> getAllPersonsAlive(int year) {
        return personRepository.findLivingAuthors(year);
    }

    public List<Book> getBooksByLanguage(String selectedLanguage) {
      return bookRepository.findByLanguagesContaining(selectedLanguage);
    }

    public Book getBookBytitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public List<Book> findBooksByAuthorName(String name) {
        return bookRepository.findByAuthorNameContaining(name);
    }

    public Book getBookByDownloadCount() {
        return bookRepository.findTop1ByOrderByDownloadCountDesc();
    }
}
