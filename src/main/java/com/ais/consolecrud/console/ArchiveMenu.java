package com.ais.consolecrud.console;

import com.ais.consolecrud.model.Author;
import com.ais.consolecrud.model.Book;
import com.ais.consolecrud.model.enums.Status;
import com.ais.consolecrud.service.AuthorService;
import com.ais.consolecrud.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ArchiveMenu extends AbstractMenu {
    private final BookService bookService;
    private final AuthorService authorService;

    @Override
    public void run() {
        boolean running = true;

        do {
            ConsoleHelper.clearAndPrintHeader("Archive");

            ConsoleHelper.printMenu(
                    "Return to main menu",
                    "Book Archive",
                    "Authors archive"
            );

            try {
                switch (scanner.nextInt()) {
                    case 0:
                        running = false;
                        break;
                    case 1:
                        booksArchive();
                        break;
                    case 2:
                        authorsArchive();
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

    private void booksArchive() {
        boolean running = true;
        do {

            ConsoleHelper.clearAndPrintHeader("Book archive");

            List<Book> books = bookService.getAllSafeDeleted();
            ConsoleHelper.printCatalogMenu(
                    "Return to main archive",
                    books,
                    "Restore from archive",
                    "Remove from archive"
            );
            try {
                switch (scanner.nextInt()) {
                    case 0:
                        running = false;
                        break;
                    case 1:
                        restoreBook(books);
                        break;
                    case 2:
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

    private void restoreBook(List<Book> books) {
        Book book = selectBook(books, bookService, Status.DELETED);
        if (book == null)
            return;
        bookService.restore(book);
        System.out.println("The book was successfully restored");
        pressEnterToContinue();
    }

    private void deleteBook(List<Book> books) {
        Book book = selectBook(books, bookService, Status.DELETED);
        if (book == null)
            return;
        bookService.delete(book);
        System.out.println("The book was successfully deleted");
        pressEnterToContinue();
    }

    private void authorsArchive() {
        boolean running = true;
        do {

            ConsoleHelper.clearAndPrintHeader("Authors archive");

            List<Author> authors = authorService.getAllSafeDeleted();
            ConsoleHelper.printCatalogMenu(
                    "Return to main archive",
                    authors,
                    "Restore from archive",
                    "Remove from archive"
            );
            try {
                switch (scanner.nextInt()) {
                    case 0:
                        running = false;
                        break;
                    case 1:
                        restoreAuthor(authors);
                        break;
                    case 2:
                        deleteAuthor(authors);
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

    private void restoreAuthor(List<Author> authors) {
        Author author = selectAuthor(authors);
        if (author == null)
            return;
        authorService.restore(author);
        System.out.println("The author was successfully restored");
        pressEnterToContinue();
    }

    private void deleteAuthor(List<Author> authors) {
        Author author = selectAuthor(authors);
        if (author == null)
            return;
        authorService.delete(author);
        System.out.println("The author was successfully deleted");
        pressEnterToContinue();
    }
}
