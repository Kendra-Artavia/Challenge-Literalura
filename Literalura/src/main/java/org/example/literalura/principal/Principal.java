package org.example.literalura.principal;

import org.example.literalura.model.Book;
import org.example.literalura.model.BookData;
import org.example.literalura.model.Person;
import org.example.literalura.servive.ConsumoAPI;
import org.example.literalura.servive.ConvierteDatos;
import org.example.literalura.servive.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private Service service;


    public Principal(Service repository) {
        this.service = repository;
    }

    public void viewMenu() {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("""
                ╔════════════════════════════════════════╗
                ║           📚 LITERALURA MENU           ║
                ╠════════════════════════════════════════╣
                ║ 1 - Search book by title               ║
                ║ 2 - List registered books              ║
                ║ 3 - List registered authors            ║
                ║ 4 - List living authors in a year      ║
                ║ 5 - List books by language(s)          ║
                ║ 6 - Search book in the database        ║
                ║ 7 - Search for an author               ║
                ║ 8 - Most downloaded                    ║
                ║ 0 - Exit                               ║
                ╚════════════════════════════════════════╝
                """);

            System.out.print("Select an option: ");

            try {
                opcion = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a number.\n");
                continue;
            }

            switch (opcion) {
                case 1 -> searchBookByTitle();
                case 2 -> searchRegisteredBooks();
                case 3 -> searchRegisteredAuthors();
                case 4 -> listAliveAuthors();
                case 5 -> listBooksByLanguage();
                case 6 -> searchBookInDatabase();
                case 7 -> searchAuthorInDatabase();
                case 8 -> mostDownloaded();
                case 0 -> {
                    System.out.println("👋 Closing the application...");
                    System.exit(0);  // <-- Esto finaliza la aplicación completamente
                }
                default -> System.out.println("❌ Invalid option.\n");
            }
        }
    }




    private Book getDataBook() {

        System.out.print("Enter the name of the book you want to search: ");
        String tituloLibro = teclado.nextLine().trim();

        if (tituloLibro.isEmpty()) {
            System.out.println("❌ Title cannot be empty.");
            return null;
        }

        String encodedTitle = URLEncoder.encode(tituloLibro, StandardCharsets.UTF_8);
        System.out.println("🔍 Searching for: \"" + tituloLibro + "\"...");

        String json = consumoApi.obtenerDatos(URL_BASE + encodedTitle);
        BookData respuesta = conversor.obtenerDatos(json, BookData.class);

        if (respuesta == null || respuesta.getResults() == null || respuesta.getResults().isEmpty()) {
            System.out.println("❌ No results found for the title.");
            return null;
        }

        Book book = respuesta.getResults().get(0);

        if (book.getTitle().length() > 1000) {
            System.out.println("❌ Book title is too long to be stored (" + book.getTitle().length() + " characters).");
            return null;
        }

        System.out.println("✅ Match found:");
        System.out.println("📘 Title: " + book.getTitle());
        return book;
    }




    private void searchBookByTitle() {
        System.out.println("""
            ╔════════════════════════════════════════════╗
            ║             🔎 SEARCH BOOK BY TITLE        ║
            ╚════════════════════════════════════════════╝
            """);

        Book book = getDataBook();

        if (book == null) {
            System.out.println("⚠️  No matching book was found in the API or the title was invalid.");
            return;
        }

        if (book.getTitle().length() > 1000) {
            System.out.println("❌ The book title is too long to be saved (" + book.getTitle().length() + " characters).");
            return;
        }

        boolean added = service.addBook(book);

        if (added) {
            System.out.println("✅ Book saved successfully: " + book.getTitle());
        } else {
            System.out.println("ℹ️  The book already exists in the database: " + book.getTitle());
        }
    }





    private void searchRegisteredBooks() {
        System.out.println("""
            ╔════════════════════════════════════════════╗
            ║          📚 LIST OF REGISTERED BOOKS       ║
            ╚════════════════════════════════════════════╝
            """);

        List<Book> books = service.getAllBooksWithDetails();

        if (books.isEmpty()) {
            System.out.println("❌ No books found in the database.");
            return;
        }

        // Ordenar por título alfabéticamente
        books.sort(Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER));

        System.out.println("📄 Total books: " + books.size() + "\n");

        for (Book book : books) {
            System.out.println(book);
            System.out.println("--------------------------------------------------");
        }
    }



    private void searchRegisteredAuthors() {
        System.out.println("""
            ╔════════════════════════════════════════════╗
            ║        👤 LIST OF REGISTERED AUTHORS       ║
            ╚════════════════════════════════════════════╝
            """);

        List<Person> persons = service.getAllPersons();

        if (persons.isEmpty()) {
            System.out.println("❌ No authors found in the database.");
            return;
        }

        // Ordenar alfabéticamente por nombre
        persons.sort(Comparator.comparing(Person::getName, String.CASE_INSENSITIVE_ORDER));

        for (Person person : persons) {
            System.out.println(person);
            System.out.println("--------------------------------------------------");
        }
    }



    private void listAliveAuthors() {
        System.out.println("""
            ╔══════════════════════════════════════════════╗
            ║     📅 LIST OF AUTHORS ALIVE IN A GIVEN YEAR ║
            ╚══════════════════════════════════════════════╝
            """);

        System.out.print("🗓️ Enter a year (e.g., 1950): ");
        int year;

        try {
            year = Integer.parseInt(teclado.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input. Please enter a valid year.");
            return;
        }

        List<Person> persons = service.getAllPersonsAlive(year);

        if (persons.isEmpty()) {
            System.out.println("❌ No authors were found alive in the year " + year + ".");
            return;
        }

        // Ordenar alfabéticamente por nombre
        persons.sort(Comparator.comparing(Person::getName, String.CASE_INSENSITIVE_ORDER));

        System.out.println("\n✅ Authors alive in the year " + year + ":\n");

        for (Person person : persons) {
            System.out.println(person);
            System.out.println("--------------------------------------------------");
        }
    }




    private void listBooksByLanguage() {
        Map<Integer, String> languageOptions = Map.of(
                1, "en",  // English
                2, "es",  // Spanish
                3, "fr",  // French
                4, "it",  // Italian
                5, "nd"   // Unknown or Other
        );

        int selection = -1;

        while (selection != 0) {
            System.out.println("""
                ╔════════════════════════════════════════╗
                ║        🌍 LANGUAGE SELECTION MENU      ║
                ╠════════════════════════════════════════╣
                ║ 1 - English                            ║
                ║ 2 - Spanish                            ║
                ║ 3 - French                             ║
                ║ 4 - Italian                            ║
                ║ 5 - Other / Unknown                    ║
                ║ 0 - Exit                               ║
                ╚════════════════════════════════════════╝
                """);

            try {
                System.out.print("Select an option: ");
                selection = teclado.nextInt();
                teclado.nextLine(); // clear buffer

                if (selection == 0) {
                    System.out.println("👋 Exiting language search...");
                    return;
                }

                String selectedLanguage = languageOptions.get(selection);

                if (selectedLanguage != null) {
                    System.out.println("\n🔍 Searching books in language: " + selectedLanguage + "\n");

                    List<Book> books = service.getBooksByLanguage(selectedLanguage);

                    if (books.isEmpty()) {
                        System.out.println("❌ No books found in this language.");
                    } else {
                        books.forEach(book -> {
                            System.out.println(book);
                            System.out.println("--------------------------------------------------");
                        });
                    }

                } else {
                    System.out.println("⚠️  Invalid option. Please try again.\n");
                }

            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input. Please enter a number.\n");
                teclado.nextLine(); // clear buffer
            }
        }
    }



    private void mostDownloaded() {
        System.out.println("""
            ╔══════════════════════════════════════════════╗
            ║      📥 MOST DOWNLOADED BOOK IN DATABASE     ║
            ╚══════════════════════════════════════════════╝
            """);

        Book book = service.getBookByDownloadCount();

        if (book == null) {
            System.out.println("❌ No books available in the database.");
            return;
        }

        System.out.println("✅ Top downloaded book:");
        System.out.println(book);
    }


    private void searchBookInDatabase() {
        System.out.println("""
            ╔════════════════════════════════════════════╗
            ║          🔎 SEARCH BOOK IN DATABASE        ║
            ╚════════════════════════════════════════════╝
            """);

        System.out.print("📖 Enter the book title: ");
        String name = teclado.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("❌ Title cannot be empty.");
            return;
        }

        Book book = service.getBookBytitle(name);

        if (book != null) {
            System.out.println("✅ Book found:\n");
            System.out.println(book);
        } else {
            System.out.println("❌ No book found with that title.");
        }
    }


    private void searchAuthorInDatabase() {
        System.out.println("""
            ╔════════════════════════════════════════════╗
            ║          👤 SEARCH BOOKS BY AUTHOR         ║
            ╚════════════════════════════════════════════╝
            """);

        System.out.print("🖋️ Enter the author's name: ");
        String name = teclado.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("❌ Author name cannot be empty.");
            return;
        }

        List<Book> books = service.findBooksByAuthorName(name);

        if (books.isEmpty()) {
            System.out.println("❌ No books found for this author.");
        } else {
            System.out.println("✅ Books found for author \"" + name + "\":\n");
            books.forEach(book -> {
                System.out.println(book);
                System.out.println("--------------------------------------------------");
            });
        }
    }






}

