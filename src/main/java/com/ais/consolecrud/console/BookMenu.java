package com.ais.consolecrud.console;


import com.ais.consolecrud.model.Author;
import com.ais.consolecrud.model.Book;
import com.ais.consolecrud.model.enums.Genre;
import com.ais.consolecrud.model.enums.Status;
import com.ais.consolecrud.service.AuthorService;
import com.ais.consolecrud.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class BookMenu extends AbstractMenu {

    private static final String HEADER = "Book catalog";
    private final BookService bookService;
    private final AuthorService authorService;

    @Override
    public void run() {
        listMenu(null);
    }

    void listMenu(Author author) {
        boolean running = true;
        List<Book> books;
        do {
            List<String> actions = new ArrayList<>();
            if (author == null) {
                books = bookService.getAll();
                actions.add("Add a book");
            }
            else {
                books = bookService.getByAuthor(author);
            }
            actions.add("Edit book");
            actions.add("Move a book to the archive");
            ConsoleHelper.clearAndPrintHeader(HEADER);
            ConsoleHelper.printCatalogMenu(
                    "Come back",
                    books,
                    actions.toArray(String[]::new)
            );

            try {
                switch (scanner.nextInt()) {
                    case 0:
                        running = false;
                        break;
                    case 1:
                        addBook();
                        break;
                    case 2:
                        editBook(books);
                        break;
                    case 3:
                        deleteBook(books);
                        break;
                    default:
                        running = isContinue("No such action exists");
                        break;
                }
            } catch (InputMismatchException e) {
                running = isContinue();
            }
        } while (running);
    }


    private void addBook() {
        Book book = new Book();

        ConsoleHelper.clearAndPrintHeader(HEADER);
        System.out.print("Enter book title: ");
        book.setName(scanNextLine());

        book.setAuthor(selectAuthor());

        book.setGenre(selectGenre());

        book.setWrittenYear(getYear("writing"));

        book.setPublicationYear(getYear("publishing"));

        ConsoleHelper.clearAndPrintHeader(HEADER);
        System.out.print("Enter publisher: ");
        book.setPublishing(scanNextLine());

        bookService.save(book);

        System.out.println("Book added successfully");

        pressEnterToContinue();
    }

    private void editBook(List<Book> books) {
        Book book = selectBook(books, bookService, Status.ACTIVE);
        if (book == null)
            return;
        boolean running;
        do {
            ConsoleHelper.clearAndPrintHeader(String.format("""
                    Edit book %s
                    Select the parameter you want to edit
                    """, book.getInfo()));

            ConsoleHelper.printMenu(
                    "Cancel edit",
                    "Title",
                    "Author",
                    "Genre",
                    "Writing year",
                    "Publishing year",
                    "Publisher"
            );

            try {
                switch (scanner.nextInt()) {
                    case 0:
                        running = false;
                        break;
                    case 1:
                        System.out.print("Enter book title: ");
                        book.setName(scanNextLine());
                        running = isContinueEdit();
                        break;
                    case 2:
                        book.setAuthor(selectAuthor());
                        running = isContinueEdit();
                        break;
                    case 3:
                        book.setGenre(selectGenre());
                        running = isContinueEdit();
                        break;
                    case 4:
                        book.setWrittenYear(getYear("writing"));
                        running = isContinueEdit();
                        break;
                    case 5:
                        book.setPublicationYear(getYear("publishing"));
                        running = isContinueEdit();
                        break;
                    case 6:
                        System.out.print("Enter publisher: ");
                        book.setPublishing(scanNextLine());
                        running = isContinueEdit();
                        break;
                    default:
                        running = isContinue("No such action exists");
                        break;
                }
            } catch (InputMismatchException e) {
                running = isContinue();
            }
        } while (running);

        bookService.save(book);
        System.out.println("Book edited successfully");
        pressEnterToContinue();
    }

    private void deleteBook(List<Book> books) {
        Book book = selectBook(books, bookService, Status.DELETED);
        if (book == null)
            return;
        bookService.safeDelete(book);
        System.out.println("The book was successfully moved to the archive");
        pressEnterToContinue();
    }

    private Author selectAuthor() {
        do {
            ConsoleHelper.clearAndPrintHeader(HEADER);
            System.out.println("Select a book author: ");

            List<Author> authors = authorService.getAll();
            ConsoleHelper.printElements(authors);

            System.out.print("To add a new author enter 0: ");
            try {
                int i = scanner.nextInt() - 1;

                if (i == -1)
                    return addAuthor();

                return authors.get(i);
            } catch (InputMismatchException | IndexOutOfBoundsException e) {
                System.out.println("Invalid author number");
            }

            pressEnterToContinue();
        } while (true);
    }

    private Author addAuthor() {
        Author author = new Author();

        ConsoleHelper.clearAndPrintHeader(HEADER);
        System.out.print("Enter the author's firstname: ");
        author.setFirstName(scanNextLine());

        ConsoleHelper.clearAndPrintHeader(HEADER);
        System.out.print("Enter author's lastname: ");
        author.setLastName(scanner.nextLine());

        authorService.save(author);

        System.out.println("Author successfully saved");

        pressEnterToContinue();

        return author;
    }

    private Genre selectGenre() {
        do {
            ConsoleHelper.clearAndPrintHeader(HEADER);
            System.out.println("Choose a book genre: ");

            List<Genre> genres = List.of(Genre.values());
            ConsoleHelper.printElements(genres.stream().map(Genre::getName).toList());

            try {
                int i = scanner.nextInt() - 1;

                if (genres.get(i) != null)
                    return genres.get(i);
                else
                    System.out.println("Incorrect genre number");

            } catch (InputMismatchException | IndexOutOfBoundsException e) {
                System.out.println("Incorrect genre number");
            }

            pressEnterToContinue();
        } while (true);
    }

    private Integer getYear(String name) {
        do {
            ConsoleHelper.clearAndPrintHeader(HEADER);
            System.out.printf("Enter %s year: ", name);

            try {
                int i = scanner.nextInt();

                if (i > -4000 && i < new GregorianCalendar().get(Calendar.YEAR))
                    return i;
                else
                    System.out.printf("%s year must be in -4000 - %s%n", name, new GregorianCalendar().get(Calendar.YEAR));
            } catch (InputMismatchException e) {
                System.out.printf("Invalid %s year%n", name);
            }

            pressEnterToContinue();
        } while (true);
    }
}