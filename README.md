
# 📚 Literalura

Literalura is a Java console application that allows users to explore books from the [Project Gutenberg API](https://gutendex.com/) and manage a local collection with PostgreSQL and Spring Boot.

## 🚀 Features

- 🔎 **Search for books by title** using the Gutendex API.
- 📥 **Save books** with their metadata to a PostgreSQL database.
- 👤 **List registered authors** and books.
- 📅 **Find authors alive in a given year**.
- 🌍 **Filter books by language** (English, Spanish, French, Italian, Others).
- 📘 **Search books or authors** directly from the database.
- 📈 **Get the most downloaded book** from the collection.

## 🧰 Technologies Used

- Java 21
- Spring Boot 3.5.3
- PostgreSQL
- Maven
- JPA (Hibernate)
- Lombok

## 🏁 Getting Started

### Prerequisites

- Java 21+
- Maven
- PostgreSQL database running

### Clone the repository

```bash
git clone https://github.com/your-username/literalura.git
cd literalura
````

### Setup your PostgreSQL connection

Update the `application.properties` file with your database credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### Run the application

```bash
mvn spring-boot:run
```

You will see the **Literalura Menu** in the terminal:

```
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
```

## 📦 Project Structure

* `model/` — Entities like `Book` and `Person`.
* `servive/` — Service layer for business logic and API consumption.
* `principal/` — The `Principal` class which drives the console UI.
* `LiteraluraApplication.java` — Main class using `CommandLineRunner`.

##  👨‍🏫 Educational Project
This project is part of the Oracle Next Education (ONE) program in collaboration with Alura Latam. Its main goal is to apply Java, Spring Boot, and database skills by building a functional book catalog integrated with an external API.

## 📝 License

This project is for educational purposes and does not include a specific license. You can adapt or extend it as needed.

---

## 👨‍💻 Author

Made with ❤️ by Kendra Artavia Caballero . Feel free to contribute!



