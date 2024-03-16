package com.ais.consolecrud.console;

import com.ais.consolecrud.model.Author;
import com.ais.consolecrud.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthorsMenu extends AbstractMenu {

    private static final String HEADER = "Authors catalog";

    private final AuthorService authorService;
    private final BookMenu bookMenu;

    @Override
    public void run() {
        boolean running = true;

        do {
            ConsoleHelper.clearAndPrintHeader(HEADER);
            List<Author> authors = authorService.getAll();

            ConsoleHelper.printCatalogMenu(
                    "Return to main menu",
                    authors,
                    "Add author",
                    "Edit author",
                    "Move author to archive",
                    "List the author's books"
            );

            try {
                switch (scanner.nextInt()) {
                    case 0:
                        running = false;
                        break;
                    case 1:
                        addAuthor();
                        break;
                    case 2:
                        editAuthor(authors);
                        break;
                    case 3:
                        deleteAuthor(authors);
                        break;
                    case 4:
                        Author author = selectAuthor(authors);
                        if (author == null)
                            break;

                        bookMenu.listMenu(author);
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

    private void addAuthor() {
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

    }

    private void editAuthor(List<Author> authors) {
        Author author = selectAuthor(authors);
        if (author == null)
            return;
        boolean running;
        do {
            ConsoleHelper.clearAndPrintHeader(String.format("""
                    Author edit %s
                    Select the parameter you want to edit
                    """, author.getInfo()));

            ConsoleHelper.printMenu(
                    "Cancel edit",
                    "Firstname",
                    "Lastname"
            );

            try {
                switch (scanner.nextInt()) {
                    case 0:
                        running = false;
                        break;
                    case 1:
                        System.out.print("Enter the author's firstname: ");
                        author.setFirstName(scanNextLine());
                        running = isContinueEdit();
                        break;
                    case 2:
                        System.out.print("Enter author's lastname: ");
                        author.setLastName(scanNextLine());
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

        authorService.save(author);
        System.out.println("Author successfully changed");
        pressEnterToContinue();
    }

    private void deleteAuthor(List<Author> authors) {
        Author author = selectAuthor(authors);
        if (author == null)
            return;
        authorService.safeDelete(author);
        System.out.println("The author was successfully moved to the archive");
        pressEnterToContinue();
    }
}
