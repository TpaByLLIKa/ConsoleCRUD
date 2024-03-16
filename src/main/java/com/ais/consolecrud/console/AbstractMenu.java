package com.ais.consolecrud.console;


import com.ais.consolecrud.model.Author;
import com.ais.consolecrud.model.Book;
import com.ais.consolecrud.model.enums.Status;
import com.ais.consolecrud.service.BookService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public abstract class AbstractMenu {
    protected Scanner scanner = new Scanner(System.in);

    public abstract void run();

    protected boolean isContinue() {
        return isContinue("Invalid input");
    }

    protected boolean isContinue(String errMsg) {
        return yesOrNoMenu(errMsg + """
                Continue?
                """);
    }

    protected void pressEnterToContinue() {
        System.out.println("Press Enter to continue");
        scanNextLine();
    }

    protected boolean yesOrNoMenu(String header) {
        do {
            ConsoleHelper.clearAndPrintHeader(header);
            ConsoleHelper.printMenu(
                    "No",
                    "Yes"
            );
            try {
                switch (scanner.nextInt()) {
                    case 0:
                        return false;
                    case 1:
                        return true;
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
                try {
                    switch (scanner.nextInt()) {
                        case 0:
                            return false;
                        case 1:
                            return true;
                    }
                } catch (InputMismatchException ignore) {
                    return true;
                }
            }
        } while (true);
    }

    protected Book selectBook(List<Book> books, BookService bookService, Status status) {
        do {
            if (yesOrNoMenu("Search by Title?")) {
                System.out.println("Enter the book title");
                scanner.nextLine();
                books = bookService.getAllByName(scanner.nextLine(), status);
            }

            ConsoleHelper.clearAndPrintHeader("Books:\n");
            ConsoleHelper.printElements(books);

            System.out.println("Enter book number or 0 to cancel: ");

            try {
                int i = scanner.nextInt() - 1;
                if (i == -1)
                    return null;

                return books.get(i);
            } catch (InputMismatchException | IndexOutOfBoundsException e) {
                System.out.println("Invalid book number");
            }
        } while (true);
    }


    protected Author selectAuthor(List<Author> authors) {
        do {
            System.out.print("Enter author number or 0 to cancel: ");

            try {
                int i = scanner.nextInt() - 1;
                if (i == -1)
                    return null;

                return authors.get(i);
            } catch (InputMismatchException | IndexOutOfBoundsException e) {
                System.out.println("Invalid author number");
            }
        } while (true);
    }

    protected boolean isContinueEdit() {
        return yesOrNoMenu("Continue edit?");
    }

    protected String scanNextLine() {
        scanner.nextLine();
        return scanner.nextLine();
    }
}
